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
import jp.ac.jec.jz.gr03.entity.*;
import jp.ac.jec.jz.gr03.util.Authorizer;
import jp.ac.jec.jz.gr03.util.CSRFTokenSet;
import jp.ac.jec.jz.gr03.util.Flash;
import jp.ac.jec.jz.gr03.util.Flash.FlashQueue;

/**
 *
 * @author yada
 */
public class LoginWithPasswordServlet extends HttpServlet {

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
        response.setCharacterEncoding("UTF-8");
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

        if (auth.hasLoggedIn()) {
            Flash.get(session).danger.offer("すでにログイン済みです。");
            response.sendRedirect("MyPageServlet");
            return;
        }
        request.setAttribute("token", CSRFTokenSet.get(session));
        request.getRequestDispatcher("loginWithPassword.jsp").forward(request, response);
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

        if (auth.hasLoggedIn()) {
            Flash.get(session).danger.offer("すでにログイン済みです。");
            response.sendRedirect("MyPageServlet");
            return;
        }
        
        // パラメータちゃんとある？
        FlashQueue errors = Flash.get(session).danger;
        String paramError = null;
        String email = request.getParameter("email");
        String rawPass = request.getParameter("password");
        String token = request.getParameter("token");
        if (email == null || rawPass == null || token == null) {
            errors.offer("パラメータが足りません");
        } else if (!token.equals(CSRFTokenSet.get(session))) {
            errors.offer("CSRF を検出");
        }
        
        // なんかエラーあった？
        if (!errors.isEmpty()) {
            request.getRequestDispatcher("loginWithPassword.jsp").forward(request, response);
            return;
        }
        

        User user = selectUser(email);
        if (user != null && user.passwordEquals(rawPass)) {
            // emailで取得できた かつ パスワードが合ってる
            // セッション固定攻撃防止
            session.invalidate();
            session = request.getSession(true);
            auth = new Authorizer(session);
            auth.loginAs(user);
            response.sendRedirect("MyPageServlet");
        } else {
            errors.offer("メールアドレスかパスワードが違います");
            request.getRequestDispatcher("loginWithPassword.jsp").forward(request, response);
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

    private User selectUser(String email) throws IOException {
        UserDAO dao = new UserDAO();
        return dao.selectByEmail(email);
    }
}
