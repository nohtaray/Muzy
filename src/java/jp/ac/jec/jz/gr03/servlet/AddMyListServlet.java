/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
<<<<<<< HEAD

package jp.ac.jec.jz.gr03;
=======
package jp.ac.jec.jz.gr03.servlet;
>>>>>>> mulist-edit系追加しました

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
<<<<<<< HEAD
=======
import java.sql.ResultSet;
>>>>>>> mulist-edit系追加しました
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
<<<<<<< HEAD
=======
import jp.ac.jec.jz.gr03.dao.MyListDAO;
import jp.ac.jec.jz.gr03.entity.MyList;
>>>>>>> mulist-edit系追加しました
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.util.Authorizer;

/**
 *
 * @author 12jz0121
 */
public class AddMyListServlet extends HttpServlet {
<<<<<<< HEAD
    static {
        try {
                Class.forName("com.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException e ){
                throw new RuntimeException(e);
        }
     }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PreparedStatement ps;
        Connection con = null;
        try (PrintWriter out = response.getWriter()) {
            con = DriverManager.getConnection("jdbc:mysql://gr03.jz.jec.ac.jp:3306/muzy?zeroDateTimeBehavior=convertToNull", "12jz0121", "12jz0121");  
            HttpSession session = request.getSession();
            Authorizer auth = new Authorizer(session);
            User user = auth.getUserLoggedIn();
            
            ps = con.prepareStatement("insert into mylists(user_id, created_at, updated_at)values(?, now(), now())");
            ps.setInt(1, user.userId);
            ps.executeUpdate();
            
           // ps = con.prepareStatement("insert into mylist_details(mylist_id, mylist_detail_id, created_at, updated_at)"
           //                         + "values((select mylist_id from mylists where updated_at = (select max(updated_at) from mylists where user_id = ?) AND user_id = ?), ?, now(), now())");
            ps = con.prepareStatement("isnert into mylist_details(mylist_id, mylist_detail_id, created_at, updated_at)"
                                    + "values((select mylist_id from my_lists where user_id = ?), ?, created_at, updated_at)");
            ps.setInt(1, user.userId);
            ////mylist_detail_id
            //ps.setInt(2, x);
            ps.executeUpdate();
=======

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        MyList mylist = new MyList();
        MyListDAO mylistDAO = new MyListDAO();

        HttpSession session = request.getSession();
        Authorizer auth = new Authorizer(session);
        User user = auth.getUserLoggedInAs();

        if(mylist.user.userId != null){
            mylist.user.userId = user.userId;
            mylist.name = request.getParameter("name");
            try {
                mylistDAO.insert(mylist);
            } catch (IOException e) {
                response.sendError(401);
            }
        }else {
            ///ログインしてないエラー
>>>>>>> mulist-edit系追加しました
        }
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
        } catch (SQLException ex) {
            Logger.getLogger(AddMyListServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(AddMyListServlet.class.getName()).log(Level.SEVERE, null, ex);
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
