
package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.dao.MyListDAO;
import jp.ac.jec.jz.gr03.dao.entityresultset.MyListResultSet;
import jp.ac.jec.jz.gr03.entity.Mylist;
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.util.Authorizer;
import jp.ac.jec.jz.gr03.entity.MyList_Details;
import jp.ac.jec.jz.gr03.dao.MusicMylistDetailDAO;
import jp.ac.jec.jz.gr03.dao.MyListDetailDAO;
import jp.ac.jec.jz.gr03.dao.entityresultset.MyListDetailResultSet;

/**
 *
 * @author 12jz0121
 */


public class MyListServlet extends HttpServlet {

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
            if (!auth.hasLoggedIn()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ログインしてください");
            }

            MyListResultSet mylists = fetchLatestMusics(auth.getUserLoggedInAs().userId);
            //MyListDetailResultSet mylistDetail = fetchMyListDetails(mylists.readRow().mylist_id);
            //request.setAttribute("mylistDetail", mylistDetail);
            ResultSet rs = fetchYoutube(auth.getUserLoggedInAs().userId);
            request.setAttribute("mylistThumbnail", rs);
            request.setAttribute("mylists", mylists);
            request.getRequestDispatcher("mylist.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(MyListServlet.class.getName()).log(Level.SEVERE, null, ex);
        }    }

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
        
        MyListDAO dao = new MyListDAO();
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
    private MyListResultSet fetchLatestMusics(int userId) throws IOException {
        MyListDAO dao = new MyListDAO();
        return dao.selectByUserId(userId);
    }
    private MyListDetailResultSet fetchMyListDetails(int myListId) throws IOException, SQLException {
        MyListDetailDAO dao = new MyListDetailDAO();
        return dao.selectLatestsByMylistId(myListId);
    }
    private ResultSet fetchYoutube(int userId) throws IOException, SQLException {
        MyListDAO dao = new MyListDAO();
        return dao.mylistDetailById(userId);
    }
}
