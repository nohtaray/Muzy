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
import jp.ac.jec.jz.gr03.dao.TagDAO;
import jp.ac.jec.jz.gr03.dao.TagScoreDAO;
import jp.ac.jec.jz.gr03.entity.Tag;
import jp.ac.jec.jz.gr03.entity.TagScore;
import jp.ac.jec.jz.gr03.entity.User;

/**
 *
 * @author 12jz0129
 */
public class EvaluationTagsServlet extends HttpServlet {

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
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ログインしてください");
            return;
        }
        User user = auth.getUserLoggedInAs();

        // パラメータチェック
        String strTagId = request.getParameter("tagid");
        String strEval = request.getParameter("evaluation");
        if (strTagId == null || strEval == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが足りません");
            return;
        }
        
        // それぞれ int に変換
        int tagId;
        try {
            tagId = Integer.parseInt(strTagId);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "tagid は数値で指定してください");
            return;
        }
        int eval;
        try {
            eval = Integer.parseInt(request.getParameter("evaluation"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "evaluation は数値で指定してください");
            return;
        }

        TagDAO tagDAO = new TagDAO();
        Tag tag = tagDAO.selectById(tagId);
        if (tag == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "タグがありません");
            return;
        }
        
        TagScoreDAO tagScoreDAO = new TagScoreDAO();
        TagScore tagScore = tagScoreDAO.selectById(user.userId, tagId);
        if (tagScore != null) {
            // 2回目以降の評価
            tagScore.score = eval;
            tagScoreDAO.update(tagScore);
        } else {
            // 初めての評価
            tagScore = new TagScore();
            tagScore.user = user;
            tagScore.tag = tagDAO.selectById(tagId);
            tagScore.score = eval;
            tagScoreDAO.insert(tagScore);
        }
        // タグの表示ごとに評価を計算するのは大変なので、追加時のみ計算する
        tagDAO.updateScores(tagDAO.selectById(tagId));
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

}
