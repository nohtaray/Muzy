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

/**
 *
 * @author yada
 */
public class SearchMusicServlet extends HttpServlet {

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

        MusicResultSet musics;
        
        String keyword = request.getParameter("q");
        String tag = request.getParameter("t");
        if (keyword != null) {
            musics = searchMusic(keyword);
            request.setAttribute("keyword", keyword);
        } else if (tag != null) {
            musics = searchTag(tag);
            request.setAttribute("tag", tag);
        } else {
            // キーワードもタグもない
            // TODO: 単独の検索ページを出す
            
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータがありません");
            return;
        }
        request.setAttribute("musics", musics);
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

    private MusicResultSet searchMusic(String keyword) throws IOException {
        MusicDAO dao = new MusicDAO();
        return dao.selectByKeyword(keyword);
    }
    private MusicResultSet searchTag(String tagName) throws IOException {
        TagDAO dao = new TagDAO();
        return dao.selectMusicsByName(tagName);
    }
}
