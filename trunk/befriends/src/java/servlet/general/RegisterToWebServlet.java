/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.general;

import business.Account;
import data.access.AccountDAO;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class RegisterToWebServlet extends HttpServlet {

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
         // check if user logged in the system
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");
        if (acc != null) {
            log("user already logged");
            // that means user already logged, so go to his home page
            response.sendRedirect("home.jsp");
            return;
        }         

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String emailAddress = request.getParameter("emailAddress");
        String gender = request.getParameter("gender");
        String month = request.getParameter("month");
        String day = request.getParameter("day");
        String year = request.getParameter("year");
        Date birthday = null;
        SimpleDateFormat formater = new SimpleDateFormat("yyyy/mm/dd");
        try {
            birthday = formater.parse(year + "/" + month + "/" + day);
            log("birthday: " + birthday);
        } catch (ParseException ex) {
            Logger.getLogger(RegisterToWebServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        acc = new Account();
        acc.setUsername(username);
        acc.setPassword(password);
        acc.setEmailAddress(emailAddress);
        acc.setGender(gender);
        acc.setBirthday(birthday);
        
        // check if username and email address are unique
        StringBuilder builder = new StringBuilder();
        if (!AccountDAO.isUniquenessRight(acc, builder)) {
            log("username or email address is not unique");
            // username or email address is not unique
            String message = builder.toString();
            request.setAttribute("message", message);
            request.setAttribute("account", acc);
            gotoPage(request, response, "/register_to_web.jsp");
            return;
        }
        else {
            AccountDAO.saveToDb(acc);
            log("adding success");
            gotoPage(request, response, "/home.jsp");
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
