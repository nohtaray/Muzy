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
import jp.ac.jec.jz.gr03.dao.MusicAdvertisementDAO;
import jp.ac.jec.jz.gr03.dao.entityresultset.MusicAdvertisementResultSet;
import jp.ac.jec.jz.gr03.entity.MusicAdvertisement;
import net.arnx.jsonic.JSON;

/**
 *
 * @author yada
 */
public class RankingMusicAdvertisementJsonServlet extends HttpServlet {

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
        
        MusicAdvertisementResultSet musicAdvertisements = selectMostAdvertised();
        
        // リンクしてるユーザ情報などを出力しないようフィルタリングする
        List<Map<String, Object>> outs = new ArrayList<>();
        for (MusicAdvertisement musicAdvertisement : musicAdvertisements) {
            Map<String, Object> row = new HashMap<>();
            Map<String, Object> music = new HashMap<>();
            music.put("music_id", musicAdvertisement.music.musicId);
            music.put("artist_id", musicAdvertisement.music.artist.artistId);
            music.put("youtube_video_id", musicAdvertisement.music.youtubeVideoId);
            music.put("view_count", musicAdvertisement.music.viewCount);
            music.put("title", musicAdvertisement.music.title);
            music.put("description", musicAdvertisement.music.description);
            music.put("created_at", musicAdvertisement.music.createdAt);
            music.put("updated_at", musicAdvertisement.music.updatedAt);
            row.put("music", music);
            row.put("advertisement_count", musicAdvertisement.advertisementCount);
            row.put("spent_point_sum", musicAdvertisement.spentPointsSum);
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

    private MusicAdvertisementResultSet selectMostAdvertised() throws IOException {
        MusicAdvertisementDAO dao = new MusicAdvertisementDAO();
        return dao.selectMostCounts(30, 0);
    }
}
