/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.jec.jz.gr03.servlet;

import jp.ac.jec.jz.gr03.util.Authorizer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.dao.MusicDAO;
import jp.ac.jec.jz.gr03.dao.TagDAO;
import jp.ac.jec.jz.gr03.entity.Music;
import jp.ac.jec.jz.gr03.entity.Tag;

/**
 *
 * @author 12jz0129
 */
public class SignUpTagsServlet extends HttpServlet {

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

        String tagName = request.getParameter("tagname");
        String strMusicId = request.getParameter("musicid");
        if (tagName == null || strMusicId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが足りません");
            return;
        }
        if (tagName.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "タグ名が空です");
            return;
        }

        int musicId;
        try {
            musicId = Integer.parseInt(strMusicId);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "楽曲IDが不正です");
            return;
        }

        Music music = fetchMusic(musicId);
        if (music == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "楽曲が存在しません");
            return;
        }

        Tag tag = new Tag();
        tag.music = music;
        tag.name = tagName;

        insertTag(tag);
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

    private Music fetchMusic(int musicId) throws IOException {
        MusicDAO dao = new MusicDAO();
        return dao.selectById(musicId);
    }

    private void insertTag(Tag tag) throws IOException {
        TagDAO dao = new TagDAO();
        dao.insert(tag);
    }
}
