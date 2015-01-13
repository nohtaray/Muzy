package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.dao.PointDAO;
import jp.ac.jec.jz.gr03.dao.VoteTicketGetHistoryDAO;
import jp.ac.jec.jz.gr03.dao.entityresultset.VoteTicketGetHistoryResultSet;
import jp.ac.jec.jz.gr03.entity.Point;
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.util.Authorizer;

/**
 *
 * @author yada
 */
public class TicketHistoryServlet extends HttpServlet {

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
        
        if (!auth.hasLoggedIn()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ログインしてください");
            return;
        }
        
        User user = auth.getUserLoggedInAs();
        Point point = fetchPoint(user);
        VoteTicketGetHistoryResultSet histories = fetchTicketHistories(user);
        request.setAttribute("histories", histories);
        request.setAttribute("point", point);
        request.getRequestDispatcher("ticketHistory.jsp").forward(request, response);
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

    private Point fetchPoint(User user) throws IOException {
        PointDAO dao = new PointDAO();
        return dao.selectByUserId(user.userId);
    }

    private VoteTicketGetHistoryResultSet fetchTicketHistories(User user) throws IOException {
        VoteTicketGetHistoryDAO dao = new VoteTicketGetHistoryDAO();
        return dao.selectByUserId(user.userId);
    }
}
