/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.managefriend;

import business.Account;
import data.access.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author duongna
 */
@WebServlet(name = "SearchByUsername", urlPatterns = {"/SearchByUsernameServlet"})
public class SearchByUsernameServlet extends HttpServlet {

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
        // check whether user is log
        HttpSession session = request.getSession();
        Account accTmp = (Account) session.getAttribute("account");
        if (accTmp == null) {
            //user not login 
            String message = "You need to login before using any service";
            request.setAttribute("message", message);
            gotoPage(request, response, "/login.jsp");
            return;
        }
                
        String usernameToFind = request.getParameter("usernameToFind");
        ArrayList<Account> searchResult = AccountDAO.searchByUsername(usernameToFind);
        
        // get number of results will be displayed
        String tmp = request.getParameter("numOfResults");
        int numOfResult = 0;
        int incrementOfResults = Integer.parseInt(
                getServletContext().getInitParameter("incrementOfResults"));
        if (tmp == null) {
            numOfResult = incrementOfResults;
        } else {
            numOfResult = Integer.parseInt(tmp);
        }
        
        int totalResults = searchResult.size();
        if (numOfResult < totalResults) {
            for (int i = totalResults-1; i >= numOfResult; i--) {
                searchResult.remove(i);
            }
        } else {
            numOfResult = totalResults;
        }
        
        request.setAttribute("searchResult", searchResult);
        request.setAttribute("numOfResult", numOfResult);
        request.setAttribute("totalResults", totalResults);
        request.setAttribute("usernameToFind", usernameToFind);
        request.setAttribute("incrementOfResults", incrementOfResults);
        gotoPage(request, response, "/search_by_username.jsp");
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
     * <code>GET</code> method.
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
