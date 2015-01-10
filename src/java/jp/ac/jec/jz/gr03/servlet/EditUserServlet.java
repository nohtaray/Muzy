package jp.ac.jec.jz.gr03.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jp.ac.jec.jz.gr03.dao.UserDAO;
import jp.ac.jec.jz.gr03.entity.User;
import jp.ac.jec.jz.gr03.util.Authorizer;

/**
 *
 * @author 12jz0112
 */
public class EditUserServlet extends HttpServlet {

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
        request.setAttribute("user", user);
        request.getRequestDispatcher("editUser.jsp").forward(request, response);
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

        String name = (String) request.getParameter("name");
        String email = (String) request.getParameter("email");
        String introduction = (String) request.getParameter("introduction");
        if (name == null || email == null || introduction == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "パラメータが足りません");
            return;
        }
        if (name.isEmpty() || email.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "必須のフィールドを入力してください");
            return;
        }

        User user = auth.getUserLoggedInAs();
        user.name = name;
        user.email = email;
        user.introduction = introduction;
        updateUser(user);
        
        response.getWriter().println("こうしんしました");
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

    private void updateUser(User user) throws IOException {
        UserDAO dao = new UserDAO();
        dao.update(user);
    }
}
