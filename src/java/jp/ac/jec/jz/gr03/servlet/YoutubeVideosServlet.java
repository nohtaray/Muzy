
package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.util.Authorizer;
import jp.ac.jec.jz.gr03.util.GoogleProxy;
import jp.ac.jec.jz.gr03.util.Json;

/**
 *
 * @author yada
 */
public class YoutubeVideosServlet extends HttpServlet {

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
        
        HttpSession session = request.getSession();
        Authorizer auth = new Authorizer(session);
        
        if (!auth.hasLoggedIn()) {
            response.sendError(401);
            return;
        }
        
        
        User user = auth.getUserLoggedInAs();
        updateGoogleTokens(user); // Googleのトークンは一定時間で有効期限が切れる

        Json videos;
        try {
            // これ！ → https://developers.google.com/youtube/v3/docs/playlistItems
            videos = retrieveVideosFromYoutubeOwnedBy(user);
        } catch (IOException e) {
            response.sendError(404);
            return;
        }
        // JSON 文字列として出力
        response.getWriter().println(videos.toString());
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
    
    
    private void updateGoogleTokens(User user) {
        // TODO: 書く
        // ログイン後1時間なら平気なので優先度低め
        // やりかた：https://developers.google.com/accounts/docs/OAuth2WebServer#refresh
        // おわったらDBもupdateすること
    }
    private Json retrieveVideosFromYoutubeOwnedBy(User user)
            throws IOException {
        GoogleProxy googleProxy = new GoogleProxy();
        
        return new Json(googleProxy.retrieveVideosUploaded(user.googleToken));
    }
}
