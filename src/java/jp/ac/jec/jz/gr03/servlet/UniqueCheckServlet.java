
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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.util.Authorizer;

public class UniqueCheckServlet extends HttpServlet {
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
    ResultSet rs = null;

  //  UserDAO userDAO = null;
        try {
		con = DriverManager.getConnection("jdbc:mysql://gr03.jz.jec.ac.jp:3306/muzy?zeroDateTimeBehavior=convertToNull", "12jz0121", "12jz0121");
                Statement stmt = con.createStatement();           
                String sql = "SELECT COUNT(email) FROM users where email = \"a\""; //+ "\"" + request.getParameter("email") + "\"";
               
		rs = stmt.executeQuery(sql);
                stmt.executeUpdate(sql);
                //重複なし
                
                if(rs.getInt("COUNT(email)") != 0){
                   response.setStatus(400);
                   response.sendError(400);
                }
                
		con.close();
                
  
		} catch (SQLException e) {
                    if(e.getErrorCode() == 1062){
			//重複してる
                    }
                }
       }
    }

    /*
    //columName : チェックする行の名前
    //chackData : チャックする値
    //return    : 重複ありの場合user_id、なしの場合0
    public int uniqueCheck(String columnName, String checkData) {
        // TODO: 書く
        Connection con = null;
        ResultSet rs = null;
        int result = 0;
        try {
                con = DriverManager.getConnection("jdbc:mysql://gr03.jz.jec.ac.jp:3306/muzy?zeroDateTimeBehavior=convertToNull", "12jz0121", "12jz0121");
                Statement stmt = con.createStatement();
                String sql = "SELECT user_id FROM USERS where " + columnName +" = " + checkData;
                rs = stmt.executeQuery(sql);
                result = rs.getInt("user_id");
        }catch (SQLException e){
        }
        out.print(result);
        return result;
        
    }
}*/