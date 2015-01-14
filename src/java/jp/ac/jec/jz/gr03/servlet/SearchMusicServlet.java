package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jp.ac.jec.jz.gr03.dao.MusicDAO;
import jp.ac.jec.jz.gr03.dao.TagDAO;
import jp.ac.jec.jz.gr03.dao.entityresultset.MusicResultSet;
import jp.ac.jec.jz.gr03.dao.entityresultset.TagResultSet;

/**
 *
 * @author yada
 */
public class SearchMusicServlet extends HttpServlet {

    public enum Order {
        CREATED_AT,
        COMMENT_CREATED_AT,
        VIEW,
        MYLIST,
        TAG_SCORE,
        VOTE,
        UNSPECIFIED,
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

        // ソート順の取得
        int order = Order.UNSPECIFIED.ordinal();
        String strOrder = request.getParameter("o");
        if (strOrder != null) {
            try {
                order = Integer.parseInt(strOrder);
            } catch (NumberFormatException ignored) {}
        }
        
        String keyword = request.getParameter("q");
        String tag = request.getParameter("t");
        if (keyword != null) {
            MusicResultSet musics = searchMusic(keyword, order);
            request.setAttribute("keyword", keyword);
            request.setAttribute("musics", musics);
        } else if (tag != null) {
            TagResultSet tags = searchTag(tag, order);
            request.setAttribute("tag", tag);
            request.setAttribute("tags", tags);
        }
        
        request.getRequestDispatcher("searchMusic.jsp").forward(request, response);
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

    private MusicResultSet searchMusic(String keyword, int order) throws IOException {
        MusicDAO dao = new MusicDAO();
        if (order == Order.CREATED_AT.ordinal()) {
            return dao.selectByKeyword(keyword, MusicDAO.Order.CREATED_AT);
        } else if (order == Order.COMMENT_CREATED_AT.ordinal()) {
            return dao.selectByKeyword(keyword, MusicDAO.Order.COMMENT_CREATED_AT);
        } else if (order == Order.VIEW.ordinal()) {
            return dao.selectByKeyword(keyword, MusicDAO.Order.VIEW);
        } else if (order == Order.MYLIST.ordinal()) {
            return dao.selectByKeyword(keyword, MusicDAO.Order.MYLIST);
        } else {
            return dao.selectByKeyword(keyword, MusicDAO.Order.UNSPECIFIED);
        }
    }

    private TagResultSet searchTag(String tagName, int order) throws IOException {
        TagDAO dao = new TagDAO();
        return dao.selectByName(tagName);
    }
}
