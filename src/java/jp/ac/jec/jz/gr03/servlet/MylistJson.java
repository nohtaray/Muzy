package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.dao.MylistDAO;
import jp.ac.jec.jz.gr03.dao.MylistDetailDAO;
import jp.ac.jec.jz.gr03.dao.entityresultset.MylistDetailResultSet;
import jp.ac.jec.jz.gr03.entity.Mylist;
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.util.Authorizer;
import net.arnx.jsonic.JSON;

/**
 *
 * @author yada
 */
public class MylistJson extends HttpServlet {

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
        
        String strId = request.getParameter("id");
        if (strId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが足りません");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(strId);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "マイリストが存在しません");
            return;
        }
        
        Mylist mylist = fetchMylist(id);
        if (mylist == null ) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "マイリストが存在しません");
            return;
        }
        
        if (!auth.hasLoggedInAs(mylist.user)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "許可がありません");
            return;
        }
        
        MylistDetailResultSet mylistDetails = fetchMylistDetails(mylist.mylistId);
        // TODO: 出しちゃいけないものをフィルタする
        response.getWriter().println(JSON.encode(mylistDetails));
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

    private MylistDetailResultSet fetchMylistDetails(int mylistId) throws IOException {
        MylistDetailDAO dao = new MylistDetailDAO();
        return dao.selectByMylistId(mylistId);
    }
    private Mylist fetchMylist(int mylistId) throws IOException {
        MylistDAO dao = new MylistDAO();
        return dao.selectById(mylistId);
    }
}
