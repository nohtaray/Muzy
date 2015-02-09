package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.dao.MessageDAO;
import jp.ac.jec.jz.gr03.entity.Message;
import jp.ac.jec.jz.gr03.util.Authorizer;

/**
 *
 * @author yada
 */
public class DeleteMessageServlet extends HttpServlet {

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

        HttpSession session = request.getSession();
        Authorizer auth = new Authorizer(session);

        if (!auth.hasLoggedIn()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ログインしてください");
            return;
        }

        String artistIdStr = request.getParameter("artist");
        String messageIdStr = request.getParameter("message");
        if (artistIdStr == null || messageIdStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが足りません");
            return;
        }
        int artistId;
        int messageId;
        try {
            artistId = Integer.parseInt(artistIdStr);
            messageId = Integer.parseInt(messageIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "整数値で指定してください");
            return;
        }
        Message message = fetchMessage(artistId, messageId);
        if (message == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "メッセージが存在しません");
            return;
        }

        if (auth.hasLoggedInAs(message.user) || auth.hasLoggedInAs(message.artist.user) || auth.getUserLoggedInAs().isOwner) {
            message.isDeleted = true;
            updateMessage(message);
            response.sendRedirect("ArtistServlet?id=" + message.artist.artistId);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "許可がありません");
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

    private Message fetchMessage(int artistId, int messageId) throws IOException {
        MessageDAO dao = new MessageDAO();
        return dao.selectById(artistId, messageId);
    }

    private void updateMessage(Message message) throws IOException {
        MessageDAO dao = new MessageDAO();
        dao.update(message);
    }
}
