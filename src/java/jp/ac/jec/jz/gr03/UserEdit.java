package jp.ac.jec.jz.gr03;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.util.Authorizer;

/**
 *
 * @author 12jz0112
 */
public class UserEdit extends HttpServlet {

    static {
            try {
                    Class.forName("com.mysql.jdbc.Driver");
            }
            catch(ClassNotFoundException e ){
                    throw new RuntimeException(e);
            }
    }
    
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
        
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            Authorizer auth = new Authorizer(session);
            
            /* テスト用コード　ろぐいんしてることにする　*/
            User _user = new User();
            _user.userId = 10;
            _user.email = "em@il.com";
            _user.name = "aa";
            _user.introduction = "よろしくね";
            _user.setPassword("pass");
            auth.login(_user);
            
            
            if (auth.hasLoggedIn()) {
                // ログインしてる
                User user = auth.getUserLoggedIn();
                
                request.setAttribute("name", user.name);
                request.setAttribute("email", user.email);
                request.setAttribute("introduction", user.introduction);
                request.getRequestDispatcher("user-edit.jsp").forward(request, response);
            } else {
                // ログインしてない
                request.setAttribute("name", "KURIO");
                request.setAttribute("email", "test");
                request.setAttribute("introduction", "testtest");
                request.getRequestDispatcher("user-edit.jsp").forward(request, response);
            }
        } finally {
            out.close();
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
        if (!auth.hasLoggedIn()) {
            // roguixnsitenai
            return;
        }
      
        User user = auth.getUserLoggedIn();
       
        //DB connect
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://gr03.jz.jec.ac.jp:3306/muzy?zeroDateTimeBehavior=converToNull", "12jz0112", "12jz0112");
        } catch (SQLException ex) {
            Logger.getLogger(UserEdit.class.getName()).log(Level.SEVERE, null, ex);
        }
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String newpass1 = request.getParameter("newpass1");
        String newpass2 = request.getParameter("newpass2");
        String introduction = request.getParameter("introduction");
        String nowpass = request.getParameter("nowpass");
        
        
        if (user.passwordEquals(nowpass)) {
            if (email != null && newpass1 != null && newpass2 != null && introduction != null && nowpass != null && name != null && !name.equals("") && !email.equals("") && newpass1.equals(newpass2)) {
                user.setPassword(newpass1);
                try {
                    PreparedStatement ps = con.prepareStatement("update users set name = ?,email = ?,password_hash = ?,introduction = ? WHERE user_id = ?");
                    ps.setString(1, name);
                    ps.setString(2, email);
                    ps.setString(3, user.passwordHash);
                    ps.setString(4, introduction);
                    ps.setInt(5, user.userId);
                    ps.executeUpdate();
                    response.getWriter().println("更新しました");
                } catch (SQLException ex) {
                    Logger.getLogger(UserEdit.class.getName()).log(Level.SEVERE, null, ex);
                }
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

}
