
package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.dao.CommentDAO;
import jp.ac.jec.jz.gr03.dao.MusicDAO;
import jp.ac.jec.jz.gr03.dao.MyListDAO;
import jp.ac.jec.jz.gr03.dao.MyListDetailDAO;
import jp.ac.jec.jz.gr03.dao.entityresultset.CommentResultSet;
import jp.ac.jec.jz.gr03.dao.entityresultset.MyListDetailResultSet;
import jp.ac.jec.jz.gr03.dao.entityresultset.MyListResultSet;
import jp.ac.jec.jz.gr03.entity.Music;
import jp.ac.jec.jz.gr03.util.Authorizer;

/**
 *
 * @author yada
 */
public class MusicServlet extends HttpServlet {

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
        
        HttpSession session = request.getSession();
        Authorizer auth = new Authorizer(session);
        
        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが足りません");
            return;
        }
        
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID は数値で指定してください");
            return;
        }
        if (auth.getUserLoggedInAs() != null){
            MyListResultSet mylists = fetchMyLists(auth.getUserLoggedInAs().userId);
            request.setAttribute("mylists", mylists);
        }
        
        Music music = selectMusic(id);
        if (music != null) {
            music.viewCount++;
            update(music);
            request.setAttribute("me", auth.getUserLoggedInAs());
            request.setAttribute("music", music);
            request.setAttribute("comments", selectComments(music.musicId));
            request.getRequestDispatcher("music.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "楽曲が存在しません");
            return;
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
    
    
    private Music selectMusic(int musicId) throws IOException {
        MusicDAO dao = new MusicDAO();
        return dao.selectById(musicId);
    }
    private CommentResultSet selectComments(int musicId) throws IOException {
        CommentDAO dao = new CommentDAO();
        return dao.selectByMusicId(musicId);
    }
    private void update(Music music) throws IOException {
        MusicDAO dao = new MusicDAO();
        dao.update(music);
    }
    private MyListResultSet fetchMyLists(int userId) throws IOException {
        MyListDAO dao = new MyListDAO();
        return dao.selectByUserId(userId);
    }
}
