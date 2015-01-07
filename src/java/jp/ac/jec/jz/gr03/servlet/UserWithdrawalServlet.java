/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jp.ac.jec.jz.gr03.servlet;

import jp.ac.jec.jz.gr03.util.Authorizer;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import jp.ac.jec.jz.gr03.entity.User;

/**
 *
 * @author 12jz0129
 */
public class UserWithdrawalServlet extends HttpServlet {
	 @Resource(name = "jdbcTest")
    private DataSource jdbcTest;
	
	 static {
		 try {
			 Class.forName("com.mysql.jdbc.Driver");
		 }
		 catch(ClassNotFoundException e ){
			 throw new RuntimeException(e);
		 }
	 }
	 
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
			throws ServletException, IOException, ClassNotFoundException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		PreparedStatement ps;
        Connection con = null;
		try {
				
			//データベースアクセス
			con = DriverManager.getConnection("jdbc:mysql://gr03.jz.jec.ac.jp:3306/muzy?zeroDateTimeBehavior=convertToNull", "12jz0129", "12jz0129");
				
			//セッション作成
            HttpSession session = request.getSession();
            Authorizer auth = new Authorizer(session);

			//Userは別ファイルから取り出し可能
			User user = auth.getUserLoggedIn();
				
			//ユーザの退会
			if (user != null){
				
				ps = con.prepareStatement("delete from users where user_id = ?");
				ps.setInt(1, user.userId);
				ps.executeUpdate();
				
				//ログアウト
				auth.logout();
				
				request.getRequestDispatcher("").forward(request, response);
			}
		} catch (SQLException ex) {
			 Logger.getLogger(UserWithdrawalServlet.class.getName()).log(Level.SEVERE, null, ex);
			 throw new ServletException(ex);
		 } finally {
			out.close();
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
		 } catch (ClassNotFoundException ex) {
			 Logger.getLogger(UserWithdrawalServlet.class.getName()).log(Level.SEVERE, null, ex);
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
		 } catch (ClassNotFoundException ex) {
			 Logger.getLogger(UserWithdrawalServlet.class.getName()).log(Level.SEVERE, null, ex);
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
