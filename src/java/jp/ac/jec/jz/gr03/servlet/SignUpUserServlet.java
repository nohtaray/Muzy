/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.util.Authorizer;
import jp.ac.jec.jz.gr03.dao.UserDAO;
import jp.ac.jec.jz.gr03.util.Flash;

/**
 *
 * @author 12jz0121
 */
public class SignUpUserServlet extends HttpServlet {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
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
        try {
            processRequest(request, response);
            request.getRequestDispatcher("signUpUser.jsp").forward(request, response);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignUpUserServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
            HttpSession session = request.getSession();

            User user = new User();
            user.email = request.getParameter("email");
            if (user.email == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが足りません");
                return;
            }

            String mailFormat = "^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$";
            if (!user.email.matches(mailFormat)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "メールフォーマットが不正です");
                return;
            }

            user.name = request.getParameter("name");
            if (user.name == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが足りません");
                return;
            }

            user.introduction = request.getParameter("introduction");

            String rawPassword = request.getParameter("password");
            if (rawPassword == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが足りません");
                return;
            }
            //パスワードのハッシュ化
            user.setPassword(rawPassword);

            user.isValid = true;
            user.isOwner = false;

            registerUser(user);

            session.invalidate();
            session = request.getSession(true);
            Authorizer auth = new Authorizer(session);
            auth.loginAs(user);
            
            Flash.get(session).success.offer("ユーザ登録しました");
            response.sendRedirect("MyPageServlet");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignUpUserServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    private void registerUser(User user)
            throws IOException {
        UserDAO dao = new UserDAO();
        dao.insert(user);
    }
}
