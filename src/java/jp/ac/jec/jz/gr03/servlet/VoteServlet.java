package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.dao.ArtistDAO;
import jp.ac.jec.jz.gr03.dao.PointDAO;
import jp.ac.jec.jz.gr03.dao.VoteDAO;
import jp.ac.jec.jz.gr03.entity.Artist;
import jp.ac.jec.jz.gr03.entity.Point;
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.entity.Vote;
import jp.ac.jec.jz.gr03.util.Authorizer;

/**
 *
 * @author yada
 */
public class VoteServlet extends HttpServlet {

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
        
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
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
        
        // ログインしてますか
        HttpSession session = request.getSession();
        Authorizer auth = new Authorizer(session);
        if (!auth.hasLoggedIn()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ログインしてください");
            return;
        }
        
        // パラメータセット
        int id, ticket;
        try {
            id = Integer.parseInt(request.getParameter("id"));
            ticket = Integer.parseInt(request.getParameter("ticket"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが不正です");
            return;
        }
        
        User me = auth.getUserLoggedInAs();
        Artist artist = fetchArtist(id);
        if (artist == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "アーティストが存在しません");
            return;
        }
        
        try {
            vote(artist, ticket, me);
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            return;
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

    private Artist fetchArtist(int id) throws IOException {
        ArtistDAO dao = new ArtistDAO();
        return dao.selectById(id);
    }
    
    
    private void vote(Artist artist, int spendTickets, User user) throws IOException {
        // IOException ばっかで良くない気がする
        if (spendTickets < 0) {
            throw new IOException("マイナスの値は指定できません");
        }
        Point point = fetchNowPoint(user);
        if (point == null || spendTickets > point.voteTicketCount) {
            throw new IOException("チケットが足りません");
        }
        
        // ポイント更新
        point.voteTicketCount -= spendTickets;
        updatePoint(point);
        
        // 投票レコード追加
        Vote vote = new Vote();
        vote.user = user;
        vote.artist = artist;
        vote.spentTickets = spendTickets;
        insertVote(vote);
    }
    private Point fetchNowPoint(User user) throws IOException {
        PointDAO dao = new PointDAO();
        return dao.selectByUserId(user.userId);
    }
    private void updatePoint(Point point) throws IOException {
        PointDAO dao = new PointDAO();
        dao.update(point);
    }
    private void insertVote(Vote vote) throws IOException {
        VoteDAO dao = new VoteDAO();
        dao.insert(vote);
    }
}
