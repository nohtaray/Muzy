
package jp.ac.jec.jz.gr03.servlet;

import jp.ac.jec.jz.gr03.util.GoogleUserInfo;
import java.io.IOException;
import java.util.Date;
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

/**
 *
 * @author yada
 */
public class SignUpWithGoogleServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific paramError occurs
     * @throws IOException if an I/O paramError occurs
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
     * @throws ServletException if a servlet-specific paramError occurs
     * @throws IOException if an I/O paramError occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        HttpSession session = request.getSession();
        Authorizer auth = new Authorizer(session);
        
        if (auth.hasLoggedIn()) {
            response.sendRedirect("");
            return;
        }
        
        boolean googleAuthenticated = session.getAttribute("googleUserInfoForSignUp") != null;
        if (googleAuthenticated) {
            // サインアップページを表示
            request.getRequestDispatcher("signUpWithGoogle.jsp").forward(request, response);
        } else {
            // 認証ページを表示
            request.getRequestDispatcher("loginWithGoogle.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific paramError occurs
     * @throws IOException if an I/O paramError occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        HttpSession session = request.getSession();
        Authorizer auth = new Authorizer(session);
        
        if (auth.hasLoggedIn()) {
            response.sendRedirect("");
            return;
        }
        
        // Googleで認証されてるか？
        GoogleUserInfo gu = (GoogleUserInfo)session.getAttribute("googleUserInfoForSignUp");
        if (gu == null) {
            // 認証まだ。認証ページを表示
            request.getRequestDispatcher("loginWithGoogle.jsp").forward(request, response);
            return;
        }
        
        if (userExists(gu)) {
            // 登録済み
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Google ID は登録済みです");
            return;
        }
        
        // パラメータは正常？
        String paramError = null;
        String name = request.getParameter("name");
        String introduction = request.getParameter("introduction");
        if (name == null || introduction == null) {
            paramError = "パラメータが足りません";
        } else if (name.isEmpty()) {
            paramError = "名前を入力してください";
        }
        
        // なんかエラーあった？
        if (paramError != null) {
            request.setAttribute("error", paramError);
            request.getRequestDispatcher("signUpWithGoogle.jsp").forward(request, response);
            return;
        }
        
        // 登録・ログイン
        User user;
        try {
            user = createUserAndRegister(name, introduction, gu);
        } catch (IOException ex) {
            Logger.getLogger(LoginWithGoogleServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "システム内部エラー。" + ex.toString());
            request.getRequestDispatcher("signUpWithGoogle.jsp").forward(request, response);
            return;
        }
        // セッション固定攻撃防止
        session.invalidate();
        session = request.getSession(true);
        auth = new Authorizer(session);
        auth.loginAs(user);

        response.getWriter().println("アカウント登録しました！");
        session.removeAttribute("googleUserInfoForSignUp");
        /*
        request.setAttribute("flush", "アカウント登録しました");
        response.sendRedirect("");
        */
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

    private boolean userExists(GoogleUserInfo gu) throws IOException {
        UserDAO dao = new UserDAO();
        return dao.selectByGoogleUID(gu.userId) != null;
    }
    private User createUserAndRegister(String name, String introduction, GoogleUserInfo gu)
            throws IOException {
        User user = new User();
        
        user.name = name;
        user.introduction = introduction;
        user.email = gu.email;
        user.googleUID = gu.userId;
        user.googleToken = gu.accessToken;
        user.googleRefreshToken = gu.refreshToken;
        user.googleExpiresAt = new Date(gu.expiresAt * 1000);
        user.isValid = true;
        user.isOwner = false;
        
        registerUser(user);
        
        return user;
    }
    private void registerUser(User user)
            throws IOException {
        UserDAO dao = new UserDAO();
        dao.insert(user);
    }
}
