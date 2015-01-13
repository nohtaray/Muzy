package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.dao.PointDAO;
import jp.ac.jec.jz.gr03.dao.PointGetHistoryDAO;
import jp.ac.jec.jz.gr03.entity.Point;
import jp.ac.jec.jz.gr03.entity.PointGetHistory;
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.util.Authorizer;

/**
 *
 * @author yada
 */
public class BuyTicketServlet extends HttpServlet {

    private final int PRICE = 100;
    
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
        request.setAttribute("point", point);
        request.setAttribute("price", PRICE);
        request.getRequestDispatcher("buyTicket.jsp").forward(request, response);
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
        
        HttpSession session = request.getSession();
        Authorizer auth = new Authorizer(session);
        
        if (!auth.hasLoggedIn()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ログインしてください");
            return;
        }

        
        String ticketStr = request.getParameter("ticket");
        if (ticketStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが足りません");
            return;
        }

        // 数値？
        int ticket;
        try {
            ticket = Integer.parseInt(ticketStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "数値で指定してください");
            return;
        }
        
        User user = auth.getUserLoggedInAs();
        
        if (ticket <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "正の整数で指定してください");
            return;
        }
        try {
            buyTickets(ticket, user);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが不正です");
            return;
        }
        
        request.setAttribute("point", fetchPoint(user));
        request.setAttribute("price", PRICE);
        request.getRequestDispatcher("buyTicket.jsp").forward(request, response);
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

    private void buyTickets(int count, User user) throws IOException, IllegalArgumentException {
        int spends = PRICE * count;
        Point point = fetchPoint(user);
        if (spends > point.pointCount) {
            throw new IllegalArgumentException("ポイントが足りません");
        }
        
        // 購入
        point.pointCount -= spends;
        point.voteTicketCount += count;
        updatePoint(point);
        
        // 履歴追加
        PointGetHistory hist = new PointGetHistory();
        hist.user = user;
        hist.gotPoints = -spends;   // ポイント支払いなのでマイナス
        hist.description = "チケット購入";
        insertPointGetHistory(hist);
    }
    private Point fetchPoint(User user) throws IOException {
        PointDAO dao = new PointDAO();
        return dao.selectByUserId(user.userId);
    }
    private void updatePoint(Point point) throws IOException {
        PointDAO dao = new PointDAO();
        dao.update(point);
    }
    private void insertPointGetHistory(PointGetHistory hist) throws IOException {
        PointGetHistoryDAO dao = new PointGetHistoryDAO();
        dao.insert(hist);
    }
}
