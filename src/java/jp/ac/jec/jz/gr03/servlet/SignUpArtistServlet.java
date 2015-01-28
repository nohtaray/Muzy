package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.dao.ArtistDAO;
import jp.ac.jec.jz.gr03.entity.Artist;
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.util.Authorizer;
import jp.ac.jec.jz.gr03.util.Flash;
import jp.ac.jec.jz.gr03.util.Flash.FlashQueue;

/**
 *
 * @author yada
 */
public class SignUpArtistServlet extends HttpServlet {

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
        if (user.googleUID == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Google の認証情報がありません。");
            return;
        }
        if (userIsArtist(user)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "すでに登録されています");
            return;
        }

        request.getRequestDispatcher("signUpArtist.jsp").forward(request, response);
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
        if (userIsArtist(user)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "すでに登録されています");
            return;
        }

        String name = request.getParameter("name");
        String introduction = request.getParameter("introduction");
        if (name == null || introduction == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが足りません");
            return;
        }
        FlashQueue errorFlashes = Flash.get(session).danger;
        if (name.isEmpty()) {
            errorFlashes.offer("アーティスト名を入力してください");
        }
        if (introduction.isEmpty()) {
            errorFlashes.offer("プロフィールを入力してください");
        }

        if (errorFlashes.isEmpty()) {
            Artist artist = new Artist();
            artist.user = user;
            artist.name = name;
            artist.introduction = introduction;
            Flash.get(session).success.offer("アーティスト登録しました。");
            insertArtist(artist);

            response.sendRedirect("MyPageServlet");
        } else {
            request.getRequestDispatcher("signUpArtist.jsp").forward(request, response);
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

    private boolean userIsArtist(User user) throws IOException {
        ArtistDAO dao = new ArtistDAO();
        Artist artist = dao.selectByUserId(user.userId);
        return artist != null;
    }

    private void insertArtist(Artist artist) throws IOException {
        ArtistDAO dao = new ArtistDAO();
        dao.insert(artist);
    }
}
