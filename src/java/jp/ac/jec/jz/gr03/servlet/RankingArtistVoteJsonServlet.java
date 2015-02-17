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
import jp.ac.jec.jz.gr03.dao.ArtistVoteDAO;
import jp.ac.jec.jz.gr03.dao.entityresultset.ArtistVoteResultSet;
import jp.ac.jec.jz.gr03.entity.ArtistMylistDetail;
import jp.ac.jec.jz.gr03.entity.ArtistVote;
import net.arnx.jsonic.JSON;

/**
 *
 * @author yada
 */
public class RankingArtistVoteJsonServlet extends HttpServlet {

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
        
        int limit = 30;
        String strLimit = request.getParameter("limit");
        if (strLimit != null) {
            try {
                limit = Integer.parseInt(strLimit);
            } catch(NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "limit must be integer");
            }
        }
        ArtistVoteResultSet artistVotes = selectArtistVotes(limit);
        
        // リンクしてるユーザ情報などを出力しないようフィルタリングする
        List<Map<String, Object>> outs = new ArrayList<>();
        for (ArtistVote artistVote : artistVotes) {
            Map<String, Object> row = new HashMap<>();
            Map<String, Object> artist = new HashMap<>();
            Map<String, Object> user = new HashMap<>();
            artist.put("artist_id", artistVote.artist.artistId);
            user.put("user_id", artistVote.artist.user.userId);
            user.put("name", artistVote.artist.user.name);
            user.put("introduction", artistVote.artist.user.introduction);
            user.put("icon_image_file", artistVote.artist.user.iconImageFile);
            user.put("created_at", artistVote.artist.user.createdAt);
            user.put("updated_at", artistVote.artist.user.updatedAt);
            artist.put("user", user);
            artist.put("name", artistVote.artist.name);
            artist.put("introduction", artistVote.artist.introduction);
            artist.put("header_image_file", artistVote.artist.headerImageFile);
            artist.put("created_at", artistVote.artist.createdAt);
            artist.put("updated_at", artistVote.artist.updatedAt);
            row.put("artist", artist);
            row.put("vote_count", artistVote.voteCount);
            row.put("spent_tickets_sum", artistVote.spentTicketsSum);
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
    
    private ArtistVoteResultSet selectArtistVotes(int limit) throws IOException {
        ArtistVoteDAO dao = new ArtistVoteDAO();
        return dao.select(limit, 0);
    }
}
