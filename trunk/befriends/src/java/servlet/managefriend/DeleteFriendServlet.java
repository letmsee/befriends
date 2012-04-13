/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.managefriend;

import business.Account;
import data.access.FriendDAO;
import java.io.IOException;
import java.io.PrintWriter;
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
public class DeleteFriendServlet extends MyServlet {

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
        
        // get accountId of user and user's friend
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");
        int userId = acc.getAccountId();
        int friendId = Integer.parseInt(request.getParameter("friendId"));
        
        String url = "/view_friend_list.jsp";
        String message = "";
        
        // check if two users are friends now
        if (FriendDAO.areFriends(userId, friendId)) {
            // delete friend relationship
            if (FriendDAO.deleteFriend(userId, friendId)) {
                message = "Delete successfully";
                // success
                acc.setNumOfFriends(acc.getNumOfFriends()-1);
            } else {
                message = "Error at database";
            }
        } else {
            message = "Error: you are not friends any more";
        }
        
        request.setAttribute("message", message);
        gotoPage(request, response, url);
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
