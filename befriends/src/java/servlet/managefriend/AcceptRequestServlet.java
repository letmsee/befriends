/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.managefriend;

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
import org.apache.catalina.connector.Request;
import servlet.MyServlet;
import sun.text.normalizer.CharTrie;

/**
 *
 * @author duongna
 */
public class AcceptRequestServlet extends MyServlet {

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
        // check if User log in
        if (!isLoggedin(request, "account")) {
            // user not log in yet
            String message = "You must login after using any service";
            request.setAttribute("message", message);
            gotoPage(request, response, "/login.jsp");
            return;
        }
        
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");
        int targetId = acc.getAccountId(); 
        int requestId = Integer.parseInt(request.getParameter("requestId"));
                
        // check if two Users are already friends
        if (FriendDAO.areFriends(targetId, requestId)) {
            String message = "You are already friends before";
            request.setAttribute("message", message);
            gotoPage(request, response, "/view_request_list.jsp");
            return;
        }
        
        // mark that two Users are friends now
        if (RequestDAO.deleteRequest(targetId, requestId) &&
                FriendDAO.addFriends(targetId, requestId)) {
            String message = "Success: You are friends now";
            request.setAttribute("message", message);
            gotoPage(request, response, "/view_request_list.jsp");
            return;
        }
        
        String message = "Error: couldn't accept request";
        request.setAttribute("message", message);
        gotoPage(request, response, "/view_request_list.jsp");
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
