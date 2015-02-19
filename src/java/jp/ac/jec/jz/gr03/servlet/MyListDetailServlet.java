
package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.dao.MylistDAO;
import jp.ac.jec.jz.gr03.dao.MylistDetailDAO;
import jp.ac.jec.jz.gr03.dao.entityresultset.MylistDetailResultSet;
import jp.ac.jec.jz.gr03.util.Authorizer;

/**
 *
 * @author 12jz0121
 */


public class MyListDetailServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            Authorizer auth = new Authorizer(session);
            MylistDetailDAO dao = new MylistDetailDAO();
            MylistDAO mylistdao = new MylistDAO();
            
            if (!auth.hasLoggedIn()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ログインしてください");
            }
            String idStr = request.getParameter("id");
            if (idStr == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが足りません");
                return;
            }
            //MyListDetailResultSet mylistDetails = dao.selectByMylistId(Integer.parseInt(idStr));
            
            request.setAttribute("musicThumbnail", dao.musicThumbnailById(Integer.parseInt(idStr)));
            request.setAttribute("artistThumbnail", dao.artistThumbnailById(Integer.parseInt(idStr)));
            
            //request.setAttribute("mylistDetails", mylistDetails);
            request.getRequestDispatcher("mylistDetail.jsp").forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(MyListDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        
        MylistDetailDAO dao = new MylistDetailDAO();
        dao.delete(Integer.parseInt(request.getParameter("id")));
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
