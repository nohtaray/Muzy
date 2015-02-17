package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.dao.CommentDAO;
import jp.ac.jec.jz.gr03.entity.Comment;
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.util.Authorizer;

/**
 *
 * @author 12jz0121
 */
public class DeleteCommentServlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(DeleteCommentServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(DeleteCommentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        HttpSession session = request.getSession();
        Authorizer auth = new Authorizer(session);
        if (!auth.hasLoggedIn()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ログインしてください");
            return;
        }

        String strId = request.getParameter("commentid");
        if (strId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが足りません");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(strId);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "idが不正です");
            return;
        }

        Comment comment = fetchComment(id);
        if (comment == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "コメントが存在しません");
            return;
        }

        User user = auth.getUserLoggedInAs();
        if (auth.hasLoggedInAs(comment.user) || auth.hasLoggedInAs(comment.music.artist.user) || user.isOwner) {
            // 削除可能。
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "許可がありません");
            return;
        }

        comment.isDeleted = true;
        updateComment(comment);
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

    private Comment fetchComment(int id) throws IOException {
        CommentDAO dao = new CommentDAO();
        return dao.selectById(id);
    }

    private void deleteComment(int commentId) throws IOException {
        CommentDAO dao = new CommentDAO();
        dao.delete(commentId);
    }
    
    private void updateComment(Comment comment) throws IOException {
        CommentDAO dao = new CommentDAO();
        dao.update(comment);
    }
}
