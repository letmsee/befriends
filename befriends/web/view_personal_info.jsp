<%-- 
    Document   : view_personal_info
    Created on : Apr 10, 2012, 12:33:05 AM
    Author     : duongna
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="business.Account"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/include/header.jsp" />

<img src="${account.avatar}" height="200" width="200"> 

<h2>Basic info</h2>
<table>
    <tr>
        <td>Avatar url: </td>
        <td><input type="text" name="avatar" value="${account.avatar}"></td>
    </tr>
    <tr>
        <td>Username:</td>
        <td>${account.username}</td>
    </tr>
    <tr>
        <td>Email Address:</td>
        <td>${account.emailAddress}</td>
    </tr>
    <tr>
        <%-- display birthday --%>
        <%
               Account acc = (Account) request.getAttribute("account");
               Date birthday = acc.getBirthday();
               SimpleDateFormat formater = new SimpleDateFormat("dd");
               int day = Integer.parseInt(
                       formater.format(birthday));

               formater = new SimpleDateFormat("mm");
               int month = Integer.parseInt(
                       formater.format(birthday));
               
               formater = new SimpleDateFormat("yyyy");
               int year = Integer.parseInt(
                       formater.format(birthday));
               
               request.setAttribute("day", day);
               request.setAttribute("month", month);
               request.setAttribute("year", year);
        %>
        <td>Birthday</td>
        <td>
            <select name="day">
                <c:forEach begin="1" end="30" varStatus="status">
                    <c:if test="${day == status.index}">
                        <option selected>${status.index}</option>
                    </c:if>
                    <c:if test="${day != status.index}">
                        <option>${status.index}</option>
                    </c:if>
                </c:forEach>
            </select>
            
            <select name="month" >
                <c:forEach begin="1" end="12" varStatus="status">
                    <c:if test="${month == status.index}">
                        <option selected>${status.index}</option>
                    </c:if>
                    <c:if test="${month != status.index}">
                        <option>${status.index}</option>
                    </c:if>
                </c:forEach>
            </select>
            
            <select name="year">
                <option>Year</option>
                <c:forEach begin="1980" end="2012" varStatus="status">
                    <c:if test="${year == status.index}">
                        <option selected>${status.index}</option>
                    </c:if>
                    <c:if test="${year != status.index}">
                        <option>${status.index}</option>
                    </c:if>
                </c:forEach>
            </select>
        </td>                
    </tr>
    
    <tr>
        <td>Gender</td>
        <td>
            <c:if test="${account.gender == male}">
                <input type="radio" name="gender" value="male" checked>Male
            </c:if>
            
            <input type="radio" name="gender" value="female" checked="${account.gender == female}">Female
        </td>
    </tr>
    <tr>
        <td>Interested Gender:</td>
        <td>
            <input type="radio" name="interestedGender" value="male" checked="checked">Male
            <input type="radio" name="interestedGender" value="female">Female
            <input type="radio" name="interestedGender" value="both">Both
        </td>
    </tr>
</table>

<h2>Hobbies</h2>
<p>
    <c:forEach var="hobby" items="${account.hobbies}">
        ${hobby}
    </c:forEach>
</p>

<h2>Dislikes</h2>
<p>
    <c:forEach var="dislike" items="${account.dislikes}">
        ${dislike}
    </c:forEach>
</p>

<h2>Study and Job</h2>
<table border="1">
    <tr>
        <td>School:</td>
        <td>${account.career.school}</td>
    </tr>
    <tr>
        <td>Job</td>
        <td>${account.career.job}</td>
    </tr>
</table>

<h2>Residence</h2>
<table border="1">
    <tr>
        <td>Address:</td>
        <td>${account.location.address}</td>
    </tr>
    <tr>
        <td>Country:</td>
        <td>${account.location.country}</td>
    </tr>
    <tr>
        <td>Home town:</td>
        <td>${account.location.hometown}</td>
    </tr>
</table>

        <c:import url="/include/footer.jsp" />