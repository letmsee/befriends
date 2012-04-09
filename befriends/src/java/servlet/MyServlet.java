/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;

/**
 *
 * @author duongna
 */
@WebServlet(name = "MyServlet", urlPatterns = {"/MyServlet"})
public class MyServlet extends HttpServlet {

    /**
     * go to page with original request and response
     */
    public void gotoPage(HttpServletRequest request, HttpServletResponse response,
            String urlTarget) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(urlTarget);
        dispatcher.forward(request, response);
    }

    /**
     * check if User is logged in
     * @param request - HttpServletRequest object
     * @param attr - attribute name used to get from session and check log in
     * @return true if login
     */
    public boolean isLoggedin(HttpServletRequest request, String attr) throws ServletException {
        HttpSession session = request.getSession();
        if (session.getAttribute(attr) != null) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * require User to log in
     */
    public void requireLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String message = "You must login before using any service";
        request.setAttribute("message", message);
        gotoPage(request, response, "/login.jsp");
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  
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
