package jp.ac.jec.jz.gr03;

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
import java.sql.*;
import jp.ac.jec.jz.gr03.dao.UserDAO;
/**
 *
 * @author 12jz0121
 */
public class SignUpArtistServlet extends HttpServlet {
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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, ClassNotFoundException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    PreparedStatement ps;
    Connection con = null;
  //  UserDAO userDAO = null;
        try {
		con = DriverManager.getConnection("jdbc:mysql://gr03.jz.jec.ac.jp:3306/muzy?zeroDateTimeBehavior=convertToNull", "12jz0121", "12jz0121");
                HttpSession session = request.getSession();
                Authorizer auth = new Authorizer(session);
                User user = new User();
                
                user = auth.getUserLoggedIn();
                
                //パスワードのハッシュ化
		user.setPassword(request.getParameter("password"));
                
                //ユーザ追加
                //デフォルト値追加後変更
                ps = con.prepareStatement("insert into artist(artist_id, user_id, name, introduction, header_image_file)values(?,?,?,?,?,now(),now(),1,0)");
                ps.setInt(1, 12131321);
                ps.setInt(2, user.userId);
                ps.setString(3, request.getParameter("name"));
                ps.setString(4, request.getParameter("introduction"));
                ps.setString(5, request.getParameter("header_image_file"));
		ps.executeUpdate();
                
		
		con.close();
                
                out.print("登録完了");
                
		} catch (SQLException e1) {
			System.out.println(
				"SQLException: " + e1.getMessage());
			System.out.println(
				"    SQLState: " + e1.getSQLState());
			System.out.println(
				" VendorError: " + e1.getErrorCode());
		} catch (Exception e2) {
			System.out.println(
				"Exception: " + e2.getMessage());

       }
    }
}