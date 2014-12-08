
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
 * @author yada
 */
public class NewMusicServlet extends HttpServlet {

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
            // ログインしてない
            response.sendRedirect("");
            return;
        }
        
        User user = auth.getUserLoggedInAs();
        if (!isUserArtist(user)) {
            // アーティストじゃない
            response.sendRedirect("");
            return;
        }
        
        request.getRequestDispatcher("newMusic.jsp").forward(request, response);
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
            // ログインしてない
            response.sendRedirect("");
            return;
        }
        
        User user = auth.getUserLoggedInAs();
        if (!isUserArtist(user)) {
            // アーティストじゃない
            response.sendRedirect("");
            return;
        }
        
        // パラメータチェック
        String paramError = null;
        String youtubeVideoId = request.getParameter("youtubeVideoId");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        if (youtubeVideoId == null || title == null || description == null) {
            paramError = "パラメータが足りません";
        } else if (!isOwnedYouTubeVideo(youtubeVideoId, user)) {
            paramError = "不正な動画です";
        } else if (title.isEmpty()) {
            paramError = "タイトルを入力してください";
        }
        // エラーあった？
        if (paramError != null) {
            request.setAttribute("error", paramError);
            request.getRequestDispatcher("newMusic.jsp").forward(request, response);
            return;
        }
        
        // 登録
        Music music;
        try {
            music = createMusicAndRegister(youtubeVideoId, title, description, user);
        } catch (IOException e) {
            request.setAttribute("error", "サーバ内部エラー：" + e.toString());
            request.getRequestDispatcher("newMusic.jsp").forward(request, response);
            return;
        }
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
    
    
    private boolean isUserArtist(User user) {
        if (user == null) return false;
        
        try {
            ArtistDAO dao = new ArtistDAO();
            Artist artist = dao.selectByUserId(user.userId);
            
            return artist != null;
        } catch (IOException ex) {
            Logger.getLogger(NewMusicServlet.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    private boolean isOwnedYouTubeVideo(String youtubeVideoId, User user) {
        // TODO: 書く
        // 自分がYouTubeに投稿した動画か？
        // API から一発で取れそう
        return true;
    }
    private Music createMusicAndRegister(String youtubeVideoId, String title, String description, User user)
            throws IOException {
        Music music = new Music();
        
        try {
            ArtistDAO artistDAO = new ArtistDAO();
            music.artist = artistDAO.selectByUserId(user.userId);
        } catch (IOException ex) {
            Logger.getLogger(NewMusicServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        music.youtubeVideoId = youtubeVideoId;
        music.viewCount = 0;
        music.title = title;
        music.description = description;
        music.isDeleted = false;
        
        MusicDAO dao = new MusicDAO();
        dao.insert(music);
        
        return music;
    }
}
