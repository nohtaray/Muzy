
package jp.ac.jec.jz.gr03.servlet;

import java.sql.SQLException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
 * @author 12jz0121
 * 
 */


public class EvaluationCommentsServlet extends HttpServlet {
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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PreparedStatement ps;
        Connection con = null;
        try (PrintWriter out = response.getWriter()) {
            con = DriverManager.getConnection("jdbc:mysql://gr03.jz.jec.ac.jp:3306/muzy?zeroDateTimeBehavior=convertToNull", "root", "rootroot");
            HttpSession session = request.getSession();
            Authorizer auth = new Authorizer(session);
            User user = auth.getUserLoggedIn();

            //仮
            if (user == null) {user = new User(); user.userId = 29;}

            //登録したcomment_idを引っ張ってくる
            int commentid = Integer.parseInt(request.getParameter("commentid"));
            //評価を受け取る
            int eva = Integer.parseInt(request.getParameter("eva"));

            ////sql文、　NULLのcaseを作ればjavaの処理短縮できるかも
            ///SET @score = (select score from comment_scores where user_id = 29 AND comment_id = 1); 
            ///update comments set score_minus_count = (CASE WHEN @score = -1 THEN (score_minus_count - 1) ELSE score_minus_count END),
            ///score_plus_count = (CASE WHEN @score = 1 THEN score_plus_count - 1 ELSE score_plus_count END) where comment_id = 1;
            ///できたはず。
            
            if (user.userId != null) {
                try {
                    //comment_score表に追加
                    ps = con.prepareStatement("insert into comment_scores value(?, ?, ?)");
                    ps.setInt(1, user.userId);
                    ps.setInt(2, commentid);
                    ps.setInt(3, eva);

                    ps.executeUpdate();
                   
                } catch (SQLException e) {
                    switch(e.getErrorCode()){   //検地すべきエラーがあれば追加する
                        case 1062:  //一意キー制約違反
                         
                            //既に評価がされていた場合、評価の再設定をするための処理（comment_score 表のscoreを変更）
                           //comments表の、のplusまたはminusを-1する処理
                            ps = con.prepareStatement("update comments set score_minus_count = (CASE WHEN (select score from comment_scores where user_id = 29 AND comment_id = 1) = -1 THEN (score_minus_count - 1) ELSE score_minus_count END),score_plus_count = (CASE WHEN (select score from comment_scores where user_id = 29 AND comment_id = 1) = 1 THEN score_plus_count - 1 ELSE score_plus_count END) where comment_id = 1");
                        //    ps.setInt(1, user.userId);
                         //   ps.setInt(2, commentid);
                           // ps.setInt(1, eva);
                            ps.executeUpdate();
                            //comment_scoreのscoreを更新する
                            ps = con.prepareStatement("update comment_scores set score = ? where user_id = 29 AND comment_id = 1");
                            ps.setInt(1, eva);
                            //ps.setInt(2, user.userId);
                            //ps.setInt(3, commentid);
                            ps.executeUpdate();
                            break;
                        
                        default:
                            break;
                    }
                }finally{
                    if (0 < eva) {
                        //comments表のscore_plusを+1
                        ps = con.prepareStatement("update comments set score_plus_count = score_plus_count + 1 where comment_id = ?");
                        ps.setInt(1, commentid);
                        ps.executeUpdate();
                    } else {
                        //comments表のscore_minus 
                        ps = con.prepareStatement("update comments set score_minus_count = score_minus_count + 1 where comment_id = ?");
                        ps.setInt(1, commentid);
                        ps.executeUpdate();
                    }
                }
            }
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
            Logger.getLogger(EvaluationCommentsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EvaluationCommentsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
