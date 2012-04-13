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
        <td>Username:</td>
        <td>${account.username}</td>
    </tr>
    <tr>
        <td>Email Address:</td>
        <td>${account.emailAddress}</td>
    </tr>
    <tr>
        <%-- display birthday --%>
        <td>Birthday</td>
        <td>${account.birthday}</td>
    </tr>
    <tr>
        <td>Gender</td>
        <td>${account.gender}</td>
    </tr>
    <tr>
        <td>Interested Gender:</td>
        <td>${account.interestGender}</td>
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