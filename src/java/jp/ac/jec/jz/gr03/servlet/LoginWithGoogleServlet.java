package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.dao.UserDAO;
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.util.Authorizer;
import jp.ac.jec.jz.gr03.util.Date;
import jp.ac.jec.jz.gr03.util.GoogleProxy;
import jp.ac.jec.jz.gr03.util.GoogleUserInfo;
import jp.ac.jec.jz.gr03.util.Json;


/**
 *
 * @author yada
 */
public class LoginWithGoogleServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        HttpSession session = request.getSession();
        Authorizer auth = new Authorizer(session);

        // すでに Google でログインしている？
        if (auth.hasLoggedIn() && auth.getUserLoggedInAs().googleUID != null) {
            response.sendRedirect("");
        } else {
            request.getRequestDispatcher("loginWithGoogle.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        HttpSession session = request.getSession();
        Authorizer auth = new Authorizer(session);

        // すでに Google でログインしている？
        if (auth.hasLoggedIn() && auth.getUserLoggedInAs().googleUID != null) {
            response.sendRedirect("");
            return;
        }

        // TODO: CSRF防止
        // 必要なパラメータがある？
        String code = request.getParameter("code");
        if (code == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが足りません");
            return;
        }

        GoogleUserInfo gu;
        try {
            // codeからGoogleのユーザ情報を取得
            gu = retrieveGoogleUserInfo(code);
        } catch (IOException ex) {
            Logger.getLogger(LoginWithGoogleServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Google アカウント情報の取得に失敗しました");
            return;
        }

        // Googleのユーザ情報を使ってDBからユーザを取得
        User dbUser = selectUser(gu);
        boolean googleAccountRegistered = dbUser != null;
        if (!auth.hasLoggedIn()) {
            // 未ログイン。
            if (googleAccountRegistered) {
                // 登録済みアカウント。ログインする
                try {
                    updateTokens(dbUser, gu);
                } catch (IOException ex) {
                    Logger.getLogger(LoginWithGoogleServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                // セッション固定攻撃防止
                session.invalidate();
                session = request.getSession(true);
                auth = new Authorizer(session);
                auth.loginAs(dbUser);

                PrintWriter out = response.getWriter();
                out.println("ログインに成功しました！");
            } else {
                // 未登録の Google アカウント。認証情報をセッションに入れて新規登録へ
                session.setAttribute("googleUserInfoForSignUp", gu);
                response.sendRedirect("SignUpWithGoogleServlet");
            }
        } else {
            // ログイン済み。Google で追加認証したい。
            if (googleAccountRegistered) {
                PrintWriter out = response.getWriter();
                out.println("この Google アカウントはすでに登録されているため使用できません");
            } else {
                // 未登録の Google アカウント
                User me = auth.getUserLoggedInAs();
                me.googleUID = gu.userId;
                me.googleToken = gu.accessToken;
                me.googleRefreshToken = gu.refreshToken;
                me.googleExpiresAt = new Date(gu.expiresAt * 1000);
                updateUser(me);
                PrintWriter out = response.getWriter();
                out.println("認証が成功しました");
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private GoogleUserInfo retrieveGoogleUserInfo(String code)
            throws IOException {
        GoogleUserInfo gu = new GoogleUserInfo();
        GoogleProxy googleProxy = new GoogleProxy();

        Json tokens = new Json(googleProxy.retrieveTokens(code));
        gu.accessToken = tokens.get("access_token");
        gu.refreshToken = tokens.get("refresh_token");

        int expiresIn = 0;
        try {
            expiresIn = Integer.parseInt(tokens.get("expires_in"));
        } catch (NumberFormatException e) {}
        gu.expiresAt = Date.now().getTime() / 1000 + expiresIn;

        Json tokenInfo = new Json(googleProxy.retrieveTokenInfo(gu.accessToken));
        gu.userId = tokenInfo.get("user_id");
        gu.email  = tokenInfo.get("email");

        return gu;
    }
    private User selectUser(GoogleUserInfo gu) throws IOException {
        UserDAO dao = new UserDAO();
        return dao.selectByGoogleUID(gu.userId);
    }
    private void updateTokens(User user, GoogleUserInfo gu)
            throws IOException {
        user.googleToken = gu.accessToken;
        user.googleRefreshToken = gu.refreshToken;
        user.googleExpiresAt = new Date(gu.expiresAt * 1000);

        UserDAO dao = new UserDAO();

        dao.update(user);
    }

    private void updateUser(User user) throws IOException {
        UserDAO dao = new UserDAO();
        dao.update(user);
    }
}
