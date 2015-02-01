package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jp.ac.jec.jz.gr03.dao.MusicDAO;
import jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet;
import jp.ac.jec.jz.gr03.entity.Music;
import net.arnx.jsonic.JSON;

/**
 *
 * @author yada
 */
public class LatestMusicJsonServlet extends HttpServlet {

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
        
        final int size = 30;
        MusicResultSet musics = fetchLatestMusics(size);
        
        // リンクしてるユーザ情報などを出力しないようフィルタリングする
        List<Map<String, Object>> outs = new ArrayList<>();
        for (Music music : musics) {
            Map<String, Object> row = new HashMap<>();
            row.put("music_id", music.musicId);
            row.put("artist_id", music.artist.artistId);
            row.put("youtube_video_id", music.youtubeVideoId);
            row.put("view_count", music.viewCount);
            row.put("title", music.title);
            row.put("description", music.description);
            row.put("created_at", music.createdAt);
            row.put("updated_at", music.updatedAt);
            outs.add(row);
        }
        response.getWriter().print(JSON.encode(outs));
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
        
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
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

    private MusicResultSet fetchLatestMusics(int size) throws IOException {
        MusicDAO dao = new MusicDAO();
        return dao.selectLatests(size, 0);
    }
}
