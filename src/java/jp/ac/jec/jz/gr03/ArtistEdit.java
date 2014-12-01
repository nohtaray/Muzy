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
public class ArtistEdit extends HttpServlet {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

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
            _user.userId = 11;
            _user.name = "artist";
            _user.introduction = "artistテスト用紹介文";
            auth.login(_user);

            if (auth.hasLoggedIn()) {
                // ログインしてる
                User user = auth.getUserLoggedIn();

                request.setAttribute("name", user.name);
                request.setAttribute("introduction", user.introduction);
                request.getRequestDispatcher("artist-edit.jsp").forward(request, response);
            } else {
                // ログインしてない
                request.setAttribute("name", "KURIKO");
                //request.setAttribute("email", "test");
                request.setAttribute("introduction", "LOL");
                request.getRequestDispatcher("artist-edit.jsp").forward(request, response);
            }
        } finally {
            out.close();
        }
    }

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

        response.getWriter().println("1");
        //DB connect
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://gr03.jz.jec.ac.jp:3306/muzy?zeroDateTimeBehavior=convertToNull", "12jz0112", "12jz0112");
        } catch (SQLException ex) {
            Logger.getLogger(UserEdit.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().println(ex.toString());
        }

        response.getWriter().println("2");
        String name = request.getParameter("name");
        String introduction = request.getParameter("introduction");
        //String nowpass = request.getParameter("nowpass");
        // この下のif条件のやつ　&& nowpass != null 

        response.getWriter().println("3");
        if (name != null && introduction != null && !name.equals("")) {
            try {
                PreparedStatement ps = con.prepareStatement("update artists set name = ?,introduction = ? WHERE user_id = ?");
                ps.setString(1, name);

                ps.setString(2, introduction);
                ps.setInt(3, user.userId);
                ps.execute();
                response.getWriter().println("更新しました");
            } catch (SQLException ex) {
                Logger.getLogger(ArtistEdit.class.getName()).log(Level.SEVERE, null, ex);
                response.getWriter().println(ex.toString());
            }
        } else {
            response.getWriter().println("パラメータが変");
        }
        response.getWriter().println("4");
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
