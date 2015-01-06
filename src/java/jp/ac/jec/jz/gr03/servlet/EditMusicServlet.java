
package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.dao.ArtistDAO;
import jp.ac.jec.jz.gr03.dao.MusicDAO;
import jp.ac.jec.jz.gr03.entity.Artist;
import jp.ac.jec.jz.gr03.entity.Music;
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.util.Authorizer;

/**
 *
 * @author 12jz0112
 */
public class EditMusicServlet extends HttpServlet {
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
        
        if (!auth.hasLoggedIn() || !isUserArtist(auth.getUserLoggedInAs())) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "アーティスト登録が必要です");
            return;
        }
        
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
        
        Music music = selectMusic(id);
        if (music == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "楽曲が存在しません");
            return;
        } else if (!auth.hasLoggedInAs(music.artist.user)) {
            // 自分の楽曲じゃない
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "許可がありません");
            return;
        }
        
        request.setAttribute("music", music);
        request.getRequestDispatcher("editMusic.jsp").forward(request, response);
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
        
        
        String idStr = request.getParameter("id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        if (idStr == null || title == null || description == null) {
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
        
        Music music = selectMusic(id);
        if (music == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "楽曲が存在しません");
            return;
        } else if (!auth.hasLoggedInAs(music.artist.user)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "許可がありません");
            return;
        }
        
        // パラメータチェック
        String paramError = null;
        if (title.isEmpty()) {
            paramError = "タイトルを入力してください";
        }
        // エラーあった？
        if (paramError != null) {
            request.setAttribute("error", paramError);
            request.getRequestDispatcher("editMusic.jsp").forward(request, response);
            return;
        }
        
        music.title = title;
        music.description = description;
        update(music);
        response.sendRedirect("MusicServlet?id=" + music.musicId);
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

    private boolean isUserArtist(User user) throws IOException {
        if (user == null) return false;
        
        ArtistDAO dao = new ArtistDAO();
        Artist artist = dao.selectByUserId(user.userId);

        return artist != null;
    }
    private Music selectMusic(int id) throws IOException {
        MusicDAO dao = new MusicDAO();
        return dao.selectById(id);
    }
    private void update(Music music) throws IOException {
        MusicDAO dao = new MusicDAO();
        dao.update(music);
    }
}
