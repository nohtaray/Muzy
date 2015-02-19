package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.dao.ArtistDAO;
import jp.ac.jec.jz.gr03.dao.MessageDAO;
import jp.ac.jec.jz.gr03.dao.MusicDAO;
import jp.ac.jec.jz.gr03.dao.MylistDAO;
import jp.ac.jec.jz.gr03.dao.entityresultset.MessageResultSet;
import jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet;
import jp.ac.jec.jz.gr03.dao.entityresultset.MylistResultSet;
import jp.ac.jec.jz.gr03.entity.Artist;
import jp.ac.jec.jz.gr03.util.Authorizer;

/**
 *
 * @author yada
 */
public class ArtistServlet extends HttpServlet {

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

        Artist artist = selectArtist(id);
        if (artist != null) {
            request.setAttribute("artist", artist);
            request.setAttribute("me", auth.getUserLoggedInAs());
            request.setAttribute("messages", selectMessages(artist.artistId));
            request.setAttribute("musics", selectMusics(artist.artistId));
            if (auth.hasLoggedIn()) {
                request.setAttribute("mylists", fetchMyLists(auth.getUserLoggedInAs().userId));
            }
            request.getRequestDispatcher("artist.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "アーティストが存在しません");
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

    private Artist selectArtist(int id) throws IOException {
        ArtistDAO dao = new ArtistDAO();
        return dao.selectById(id);
    }

    private MessageResultSet selectMessages(int artistId) throws IOException {
        MessageDAO dao = new MessageDAO();
        return dao.selectByArtistId(artistId);
    }

    private MusicResultSet selectMusics(int artistId) throws IOException {
        MusicDAO dao = new MusicDAO();
        return dao.selectByArtistId(artistId);
    }

    private MylistResultSet fetchMyLists(int userId) throws IOException {
        MylistDAO dao = new MylistDAO();
        return dao.selectByUserId(userId);
    }
}
