package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jp.ac.jec.jz.gr03.dao.TagDAO;
import jp.ac.jec.jz.gr03.dao.entityresultset.TagResultSet;
import jp.ac.jec.jz.gr03.entity.Tag;
import net.arnx.jsonic.JSON;

/**
 *
 * @author yada
 */
public class MusicTagsServlet extends HttpServlet {

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

        String musicIdStr = request.getParameter("id");
        if (musicIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが足りません");
            return;
        }

        int musicId;
        try {
            musicId = Integer.parseInt(musicIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID は数値で指定してください");
            return;
        }

        TagResultSet tags = selectTags(musicId);
        // リンクしてるユーザ情報などを出力しないようフィルタリングする
        List<Map<String, Object>> tagList = new ArrayList<>();
        for (Tag tag : tags) {
            Map<String, Object> mp = new HashMap<>();
            mp.put("name", tag.name);
            mp.put("score_average", tag.scoreAverage);
            mp.put("score_count", tag.scoreCount);
            tagList.add(mp);
        }
        response.getWriter().print(JSON.encode(tagList));
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

    TagResultSet selectTags(int musicId) {
        TagDAO dao = new TagDAO();
        TagResultSet tags = null;
        try {
            tags = dao.selectByMusicId(musicId);
        } catch (IOException ex) {
            Logger.getLogger(MusicTagsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tags;
    }
}
