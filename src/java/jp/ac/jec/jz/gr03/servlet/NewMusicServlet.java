package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.dao.ArtistDAO;
import jp.ac.jec.jz.gr03.dao.MusicDAO;
import jp.ac.jec.jz.gr03.dao.TagDAO;
import jp.ac.jec.jz.gr03.entity.Artist;
import jp.ac.jec.jz.gr03.entity.Music;
import jp.ac.jec.jz.gr03.entity.Tag;
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
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ログインしてください");
            return;
        }

        User user = auth.getUserLoggedInAs();
        if (!isUserArtist(user)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "アーティスト登録してください");
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
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ログインしてください");
            return;
        }

        User user = auth.getUserLoggedInAs();
        if (!isUserArtist(user)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "アーティスト登録してください");
            return;
        }

        // パラメータチェック
        String paramError = null;
        String youtubeVideoId = request.getParameter("youtubeVideoId");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String strTags = request.getParameter("tags");
        if (youtubeVideoId == null || title == null || description == null || strTags == null) {
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
            Set<String> tags = parseTags(strTags);
            insertTags(music, tags);
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

    private boolean isUserArtist(User user) throws IOException {
        if (user == null) {
            return false;
        }

        ArtistDAO dao = new ArtistDAO();
        Artist artist = dao.selectByUserId(user.userId);

        return artist != null;
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

        ArtistDAO artistDAO = new ArtistDAO();
        music.artist = artistDAO.selectByUserId(user.userId);

        music.youtubeVideoId = youtubeVideoId;
        music.viewCount = 0;
        music.title = title;
        music.description = description;
        music.isDeleted = false;

        MusicDAO dao = new MusicDAO();
        dao.insert(music);

        return music;
    }

    private Set<String> parseTags(String tagsDelimitedByNewLine) {
        String[] split = tagsDelimitedByNewLine.split("[\\r\\n]");
        // 重複と空文字列を除く
        Set<String> tags = new HashSet<>();
        for (String tag : split) {
            if (tag.length() > 0) {
                tags.add(tag);
            }
        }
        return tags;
    }
    private void insertTags(Music music, Set<String> tags) throws IOException {
        TagDAO dao = new TagDAO();
        for (String tagName : tags) {
            Tag tag = new Tag();
            tag.music = music;
            tag.name = tagName;
            dao.insert(tag);
        }
    }
}
