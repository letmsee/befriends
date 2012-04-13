/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.managefriend;

import business.Account;
import data.access.RequestDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class ViewWaitingListServlet extends MyServlet {

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
        
        // get User's accountId
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");
        int accountId = acc.getAccountId();
        ArrayList<Account> waitingList = RequestDAO.getWaitingList(accountId);

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
        
        int totalResults = waitingList.size();
        if (numOfResult < totalResults) {
            for (int i = totalResults-1; i >= numOfResult; i--) {
                waitingList.remove(i);
            }
        } else {
            numOfResult = totalResults;
        }
        
        request.setAttribute("numOfResult", numOfResult);
        request.setAttribute("totalResults", totalResults);
        request.setAttribute("incrementOfResults", incrementOfResults);
        request.setAttribute("waitingList", waitingList);
        gotoPage(request, response, "/view_waiting_list.jsp");
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
