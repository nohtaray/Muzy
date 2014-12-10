
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
import jp.ac.jec.jz.gr03.entity.Music;

/**
 *
 * @author 12jz0112
 */
public class DeleteMusicByAdminServlet extends HttpServlet {

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
        
        String idStr = request.getParameter("id");
        if (idStr == null) {
            // パラメータがない
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            // idが数値じゃない
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        if (idExists(id)) {
            deleteMusic(id);
            response.sendRedirect("AdminMusicServlet");
            return;
        } else {
            // id が存在しない
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
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

    private boolean idExists(int id) {
        MusicDAO dao = new MusicDAO();
        Music music = null;
        try {
            music = dao.selectById(id);
        } catch (IOException ex) {
            Logger.getLogger(DeleteMusicByAdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return music != null;
    }
    private void deleteMusic(int id) {
        try {
            MusicDAO dao = new MusicDAO();
            Music music = dao.selectById(id);
            dao.delete(music);
        } catch (IOException ex) {
            Logger.getLogger(DeleteMusicByAdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
