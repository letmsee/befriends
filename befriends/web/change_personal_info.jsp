<%-- 
    Document   : change_personal_info
    Created on : Apr 13, 2012, 9:53:35 AM
    Author     : duongna
--%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/include/header.jsp" />

<img src="${account.avatar}" height="200" width="200"> 

<h2>Basic info</h2>
<table>
    <tr>
        <td>Avatar url:</td>
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
        <td>Birthday</td>
        <td>${account.birthday}</td>
    </tr>
    <tr>
        <%-- display gender --%>
        <td>Gender</td>
        <td>
            <select>
                <c:choose>
                    <c:when test="${account.gender == 'female'}">
                        <option selected>Female</option>
                    </c:when>
                    <c:otherwise>
                        <option>Female</option>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${account.gender == 'male'}">
                        <option selected>Male</option>
                    </c:when>
                    <c:otherwise>
                        <option>Male</option>
                    </c:otherwise>
                </c:choose>
            </select>
        </td>
    </tr>
    <tr>
        <%-- Display friend gender --%>
        <td>Friend Gender</td>
        <td>
            <select>
                <c:choose>
                    <c:when test="${account.interestGender == 'female'}">
                        <option selected>Female</option>
                    </c:when>
                    <c:otherwise>
                        <option>Female</option>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${account.interestGender == 'male'}">
                        <option selected>Male</option>
                    </c:when>
                    <c:otherwise>
                        <option>Male</option>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${account.interestGender == 'both'}">
                        <option selected>Both</option>
                    </c:when>
                    <c:otherwise>
                        <option>Both</option>
                    </c:otherwise>
                </c:choose>
            </select>
        </td>
    </tr>
</table>

<h2>Hobbies</h2>
<%-- Display Hobbies --%>
<table>
    <tr>
        <%
               HashMap<String, String> map = new HashMap<String, String>();
        %>
        <td>
            <%-- find music in hobbies array --%>
            <c:forEach var="hobby" items="${account.hobbies}">
                <c:if test="${hobby == 'music'}
            </c:forEach>
        </td>
    </tr>
</table>


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