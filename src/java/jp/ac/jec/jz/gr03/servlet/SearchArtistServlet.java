package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jp.ac.jec.jz.gr03.dao.ArtistDAO;
import jp.ac.jec.jz.gr03.dao.entityresultset.ArtistResultSet;

/**
 *
 * @author yada
 */
public class SearchArtistServlet extends HttpServlet {

    public enum Order {
        CREATED_AT,
        MYLIST,
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

        // ソート順
        int order = Order.UNSPECIFIED.ordinal();
        String strOrder = request.getParameter("o");
        if (strOrder != null) {
            try {
                order = Integer.parseInt(strOrder);
            } catch (NumberFormatException ignored) {}
        }
        
        String keyword = request.getParameter("q");
        if (keyword != null) {
            ArtistResultSet artists = searchArtist(keyword, order);
            request.setAttribute("artists", artists);
        }
        
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("searchArtist.jsp").forward(request, response);
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

    private ArtistResultSet searchArtist(String keyword, int order) throws IOException {
        ArtistDAO dao = new ArtistDAO();
        return dao.selectByKeyword(keyword, toArtistDAOOrder(order));
    }
    private ArtistDAO.Order toArtistDAOOrder(int order) {
        if (order == Order.CREATED_AT.ordinal()) {
            return ArtistDAO.Order.CREATED_AT;
        } else if (order == Order.MYLIST.ordinal()) {
            return ArtistDAO.Order.MYLIST;
        } else if (order == Order.VOTE.ordinal()) {
            return ArtistDAO.Order.VOTE;
        } else {
            return ArtistDAO.Order.UNSPECIFIED;
        }
    }
}
