/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import business.Account;
import data.access.FriendDAO;
import data.access.RequestDAO;
import java.io.IOException;
import java.io.PrintWriter;
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
public class AddFriendServlet extends HttpServlet {

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
        
        // check if two user are already friends
        int targetId = Integer.parseInt(request.getParameter("targetId"));
        int requestId = accTmp.getAccountId();
        if (FriendDAO.areFriends(requestId, targetId)) {
            // two user are friend
            String message = "Already friends";
            request.setAttribute("message", message);
            gotoPage(request, response, "/search_by_username.jsp");
            return;
        }
        
        // check if request is sent
        if (RequestDAO.accountInRequest(targetId, requestId)) {
            // request is already sent
            String message = "Request already sent";
            request.setAttribute("message", message);
            gotoPage(request, response, "/search_by_username.jsp");
            return;
        }
        
        // add request id to the request list of target User
        boolean success = RequestDAO.addAccount(targetId, requestId);
        if (!success) {
            // not successful
            String message = "Errror: can't not send quest";
            request.setAttribute("message", message);
            gotoPage(request, response, "/search_by_username.jsp");
        }
        else {
            // successfull
            String message = "Request is sent";
            request.setAttribute("message", message);
            gotoPage(request, response, "/search_by_username.jsp");
        }
    }
    
     /**
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
