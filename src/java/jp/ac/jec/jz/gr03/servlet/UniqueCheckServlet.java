
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import jp.ac.jec.jz.gr03.dao.UserDAO;
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.util.Authorizer;

public class UniqueCheckServlet extends HttpServlet {
    
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
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
            
            User user = new User();
            user.email = request.getParameter("email");
            
            String mailFormat = "^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$";
            if(!user.email.matches(mailFormat)){
                //エラー番号適当
                response.sendError(1000);
                return;
            }
                    
            if(uniqueCheckEmail(user) != null){
                response.sendError(1082);
                return;
            }
            
            
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UniqueCheckServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        

    }
    private User uniqueCheckEmail(User user)
            throws IOException {
        UserDAO dao = new UserDAO();
        return dao.selectByEmail(user.email);
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