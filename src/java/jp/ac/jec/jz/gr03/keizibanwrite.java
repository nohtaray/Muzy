/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.jec.jz.gr03;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.util.Authorizer;

/**
 *
 * @author 12jz0112
 */
public class keizibanwrite extends HttpServlet {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
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

        request.getRequestDispatcher("artist.jsp").forward(request, response);

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

        Connection con = null;
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession();
            Authorizer auth = new Authorizer(session);
            String text;
            text = request.getParameter("text");

            //host = request.getRemoteHost(); IPアドレスを取得するらしい
            if ((text == null) || text.equals("")) {
                out.println("コメントが入力されていません！");
                return;
            }
            if (text.length() <= 1) {
                out.println("コメントが短すぎます！");
                return;
            }

            //データベースに書き込む処理
            con = DriverManager.getConnection("jdbc:mysql:muzy", "dbuser", "mypassword");

            //クエリーを実行
            Integer artistId;
             try {
                artistId = Integer.parseInt(request.getParameter("artistId"));
            } catch (NumberFormatException e) {
                artistId = null;
            }
            
            String sql = "select max(message_id) from messages where artist_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, artistId);
            ResultSet rs = ps.executeQuery();

            int maxId;
            if (rs.next()) {
                // 結果が返ってきてる
                maxId = rs.getInt(1);
            } else {
                maxId = 0;
            }
            
            Integer userId;
            try {
                userId = Integer.parseInt(request.getParameter("userId"));
            } catch (NumberFormatException e) {
                userId = null;
            }
            String content = request.getParameter("content");

            sql = "insert into messages (artist_id,message_id,user_id,content,response_to_id,created_at) values (?,?,?,?,?,now())";
            ps = con.prepareStatement(sql);
            //DAOができたらnullチェックを入れます
            ps.setInt(1, artistId);
            ps.setInt(2, maxId + 1);
            ps.setInt(3, userId);
            ps.setString(4, content);
            ps.setNull(5, java.sql.Types.INTEGER);
            ps.execute();

            
        } catch (SQLException e) {
            out.println("SQLException:" + e.getMessage());
        } finally {
            out.close();
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
