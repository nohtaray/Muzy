/*Error reading included file Templates/JSP_Servlet/Templates/Licenses/license-default_1.txt*/
package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

/**
 *
 * @author 12jz0129
 */


public class AddReviewServlet extends HttpServlet {
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
	 * @throws java.lang.ClassNotFoundException
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		PreparedStatement ps;
		PreparedStatement ps2;
        Connection con = null;
		try {
            //データベースアクセス
            con = DriverManager.getConnection("jdbc:mysql://gr03.jz.jec.ac.jp:3306/muzy?zeroDateTimeBehavior=convertToNull", "12jz0129", "12jz0129");
            
            HttpSession session = request.getSession();
            Authorizer auth = new Authorizer(session);
            User user = auth.getUserLoggedInAs();
            
			//追加されたレビュー内容を引っ張ってくる
			String review = request.getParameter("review");
			
			//仮
            if (user == null) {user = new User(); user.userId = 29;}
			
            if(review != null){
                ps = con.prepareStatement("insert into comments (user_id, content, music_id, score_plus_count, score_minus_count, created_at, is_deleted) VALUES (?, ?, ?, 0, 0, now(), 0)", Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, user.userId);
				ps.setString(2, review);
				ps.setInt(3, Integer.parseInt(request.getParameter("musicid")));
				ps.executeUpdate();
			
				
			//上記のSQLで順列で生成されたconmmetIDを取得する
			//コメントした時に評価値はいらない？
				
				
				int commentid = 0;
                ResultSet gKeys = ps.getGeneratedKeys();
                if(gKeys.next()){
                    commentid = gKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating user faled, no ID obtained.");
                }
                
                ps2 = con.prepareStatement("insert into comment_scores (user_id, comment_id, score) VALUES (?, ?, 0");
                ps2.setInt(1, user.userId);
                ps2.setInt(2, commentid);
				ps2.executeUpdate();
				
				
			}
            else {
                //失敗に遷移したときにこれを書くとajaxでエラーに飛ぶ
                //何もなくうまくいったら200が自動で帰る。
                response.sendError(400);
            }
			
		} catch (SQLException ex) {
            Logger.getLogger(SignUpTagsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(AddReviewServlet.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(AddReviewServlet.class.getName()).log(Level.SEVERE, null, ex);
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
