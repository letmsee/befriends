/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.general;

import business.Account;
import business.AccountOfMatch;
import data.access.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servlet.MyServlet;

/**
 *
 * @author duongna
 */
public class WelcomeServlet extends MyServlet {

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
        if (!isLoggedin(request, "account")) {
            requireLogin(request, response);
            return;
        }
        
        // search for appropriate people
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");
        ArrayList<AccountOfMatch> searchResult = AccountDAO.searchMatch(acc.getAccountId());
        // get limit
        ServletContext context = getServletContext();
        int matchingLimit = Integer.parseInt
                (context.getInitParameter("matchingLimit"));
        if (searchResult.size() > matchingLimit) {
            for (int i = searchResult.size()-1; i >= matchingLimit; i--) {
                searchResult.remove(i);
            }
        }
        request.setAttribute("searchResult", searchResult);
        
        // rank new accounts
        ArrayList<AccountOfMatch> rankNewResult = AccountDAO.rankNewAccount(acc.getAccountId());
        // get limit
        int rankNewLimit = Integer.parseInt(
                context.getInitParameter("rankNewLimit"));
        log("rankNewResult: " + rankNewResult + " - limit:" + rankNewLimit);
        if (rankNewResult.size() > rankNewLimit) {
            for (int i = rankNewResult.size()-1; i >= rankNewLimit; i--) {
                rankNewResult.remove(i);
            }
        }
        request.setAttribute("rankNewResult", rankNewResult);
        gotoPage(request, response, "/welcome.jsp");
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
