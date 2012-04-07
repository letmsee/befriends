/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.managefriend;

import business.Account;
import data.access.DenialDAO;
import data.access.RequestDAO;
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
public class DenyRequestServlet extends MyServlet {

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
            String message = "You must login before using any service";
            request.setAttribute("message", message);
            gotoPage(request, response, "/login.jsp");
            return;
        }
        
        // get denierId and requestId
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");
        int denierId = acc.getAccountId();
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        
        String url = "/view_request_list.jsp";
        String message = null;
        
        // check if request still exists 
        if (!RequestDAO.accountInRequest(denierId, requestId)) {
            message = "Request not exist";
            request.setAttribute("message", message);
            gotoPage(request, response, url);
            return;
        }
        
        // delete request from User's request list
        RequestDAO.deleteRequest(denierId, requestId);
        
        // check if denial exists
        if (!DenialDAO.denialExists(denierId, requestId)) {
            // denial to User's denial listt
            DenialDAO.addDenial(denierId, requestId);
        }
        
        message = "Deny successfully";
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
