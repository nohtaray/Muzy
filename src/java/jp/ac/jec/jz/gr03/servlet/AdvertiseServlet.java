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
import jp.ac.jec.jz.gr03.dao.AdvertisementDAO;
import jp.ac.jec.jz.gr03.dao.MusicDAO;
import jp.ac.jec.jz.gr03.dao.PointDAO;
import jp.ac.jec.jz.gr03.entity.Advertisement;
import jp.ac.jec.jz.gr03.entity.Music;
import jp.ac.jec.jz.gr03.entity.Point;
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.util.Authorizer;

/**
 *
 * @author yada
 */
public class AdvertiseServlet extends HttpServlet {

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
        
        // ログインしてますか
        HttpSession session = request.getSession();
        Authorizer auth = new Authorizer(session);
        if (!auth.hasLoggedIn()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ログインしてください");
            return;
        }
        
        // パラメータセット
        int id, point;
        try {
            id = Integer.parseInt(request.getParameter("id"));
            point = Integer.parseInt(request.getParameter("point"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが不正です");
            return;
        }
        
        User me = auth.getUserLoggedInAs();
        Music music = fetchMusic(id);
        if (music == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "楽曲が存在しません");
            return;
        }
        
        try {
            advertise(music, point, me);
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
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

    private Music fetchMusic(int id) {
        MusicDAO dao = new MusicDAO();
        Music music = null;
        try {
            music =  dao.selectById(id);
        } catch (IOException ex) {
            Logger.getLogger(AdvertiseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return music;
    }
    private void advertise(Music music, int spendPoints, User user) throws IOException {
        // IOException ばっかで良くない気がする
        if (spendPoints < 0) {
            throw new IOException("マイナスの値は指定できません");
        }
        Point point = fetchNowPoint(user);
        if (spendPoints > point.pointCount) {
            throw new IOException("ポイントが足りません");
        }
        
        // ポイント更新
        point.pointCount -= spendPoints;
        updatePoint(point);
        
        // 広告追加
        Advertisement ad = new Advertisement();
        ad.music = music;
        ad.spentPoints = spendPoints;
        ad.user = user;
        insertAdvertisement(ad);
    }
    private Point fetchNowPoint(User user) {
        PointDAO dao = new PointDAO();
        Point point = null;
        try {
            point = dao.selectByUserId(user.userId);
        } catch (IOException ex) {
            Logger.getLogger(AdvertiseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return point;
    }
    private void insertAdvertisement(Advertisement ad) {
        AdvertisementDAO dao = new AdvertisementDAO();
        try {
            dao.insert(ad);
        } catch (IOException ex) {
            Logger.getLogger(AdvertiseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void updatePoint(Point point) {
        PointDAO dao = new PointDAO();
        try {
            dao.update(point);
        } catch (IOException ex) {
            Logger.getLogger(AdvertiseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
