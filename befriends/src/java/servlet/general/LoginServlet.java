/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.general;

import business.Account;
import data.access.AccountDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author duongna
 */
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // check if user already login
        HttpSession session = request.getSession();
        Account accTmp = (Account) session.getAttribute("account");
        if (accTmp != null) {
            log("User already logged");
            // user already login
            response.sendRedirect("home.jsp");
            return;
        }
        
        // check login information
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Account acc = new Account();
        acc.setUsername(username);
        acc.setPassword(password);
        if (!AccountDAO.loginInfoIsValid(acc)) {
            String message = "Username and password not match";
            request.setAttribute("message", message);
            request.setAttribute("account", acc);
            gotoPage(request, response, "/login.jsp");
        }
        else {
            acc = AccountDAO.getAccount(acc);
            acc = AccountDAO.getPersonalInfo(acc.getAccountId());
            session.setAttribute("account", acc);
            gotoPage(request, response, "/Welcome");
        }
    }

    /*
     * go to page with original request and response
     */
    public void gotoPage(HttpServletRequest request, HttpServletResponse response,
            String urlTarget) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(urlTarget);
        dispatcher.forward(request, response);
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>POST</code> method.
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
