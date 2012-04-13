<%-- 
    Document   : header
    Created on : Apr 3, 2012, 12:52:27 AM
    Author     : duongna
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>befriends website</title>
    </head>
    <body>
        <table border="1">
            <tr>
                <td>
                    <p>sessionId: ${pageContext.session.id}<br>
                        accountId: ${sessionScope.account.accountId}<br>
                        username: ${sessionScope.account.username}<br>
                        emailAddress: ${sessionScope.account.emailAddress}</p>
                </td>
                <td>
                    <p>General</p>
                    <a href="register_to_web.jsp">register</a><br>
                    <a href="login.jsp">login</a><br>
                    <a href="Logout">logout</a><br>
                </td>
                <td>
                    <p>Manage friend</p>
                    <a href="ViewRequestList">View Request List(${sessionScope.account.numOfRequests}) </a><br>
                    <a href="ViewWaitingList">View Waiting List(${sessionScope.account.numOfWaitings})</a><br>
                    <a href="ViewFriendList">View Friend List(${sessionScope.account.numOfFriends})</a><br>
                    <form action="/SearchMatch">
                        <input type="submit" value="Search matching">
                    </form>
                </td>
                <td>
                    <p>Manage Personal Info</p>
                    <a href="
                       <c:url value="/ViewPersonalInfo">
                           <c:param name="accountId" value="${sessionScope.account.accountId}"/>
                       </c:url>
                       ">View Personal Info</a><br>
                </td>
            <tr>
            <form action="SearchByUsername">
                <td>
                    <input type="text" name="usernameToFind">
                    <input type="button" value="Search" onclick="validateSearch(this.form)">
                </td>
            </form>
        </tr>
    </table>
    
    <%-- Java script to validate search by username --%>
    <script type="text/javascript">
        function validateSearch(form) {
            if (form.usernameToFind.value != "") {
                form.submit();
            }
        }
    </script>
    