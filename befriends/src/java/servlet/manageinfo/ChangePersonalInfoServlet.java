/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.manageinfo;

import business.Account;
import data.access.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class ChangePersonalInfoServlet extends MyServlet {

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
        
        // determine if this is the first time
        String _day = (String) request.getParameter("day");
        if (_day == null) {
            // get personal info
            HttpSession session = request.getSession();
            Account acc = (Account) session.getAttribute("account");
            acc = AccountDAO.getPersonalInfo(acc.getAccountId());
            request.setAttribute("account", acc);
            gotoPage(request, response, "/change_personal_info.jsp");
            return;
        }

        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");
        acc = AccountDAO.getPersonalInfo(acc.getAccountId());
        acc.getHobbies().clear();
        acc.getDislikes().clear();
        
        // get all the info
        
        // get gender
        String gender = request.getParameter("gender");
        String interestGender = request.getParameter("interestGender");
        acc.setGender(gender);
        acc.setInterestGender(interestGender);
        log("gender:" + gender + "-interestGender: " + interestGender);

        // get birthday
        String month = request.getParameter("month");
        String day = request.getParameter("day");
        String year = request.getParameter("year");
        Date birthday = null;
        SimpleDateFormat formater = new SimpleDateFormat("yyyy/mm/dd");
        try {
            birthday = formater.parse(year + "/" + month + "/" + day);
        } catch (Exception e) {
            e.printStackTrace();
        }
        acc.setBirthday(birthday);
        
        // get hobbies and dislikes
        setPreference(acc, request, "music");
        setPreference(acc, request, "movie");
        setPreference(acc, request, "book");
        setPreference(acc, request, "sport");
        setPreference(acc, request, "game");
        
        // get address
        String school = request.getParameter("school");
        String job = request.getParameter("job");
        acc.getCareer().setSchool(school);
        acc.getCareer().setJob(job);
        log("school:" + school + " - job:" + job);
        
        // get location
        String address = request.getParameter("address");
        String country = request.getParameter("country");
        String hometown = request.getParameter("hometown");
        acc.getLocation().setAddress(address);
        acc.getLocation().setCountry(country);
        acc.getLocation().setHometown(hometown);
        
        // save to database
        AccountDAO.updateAccount(acc);
        acc = AccountDAO.getPersonalInfo(acc.getAccountId());
        String message = "Changes are saved";
        request.setAttribute("message", message);
        request.setAttribute("account", acc);
        gotoPage(request, response, "/change_personal_info.jsp");
    }

    private void setPreference(Account acc, HttpServletRequest request, String field) {
        String fieldValue = request.getParameter(field);
        if (fieldValue.equals("like")) {
            acc.getHobbies().add(field);
            log("like: " + field + " - fieldValue:" + fieldValue);
        } else if (fieldValue.equals("dislike")) {
            acc.getDislikes().add(field);
        }
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
