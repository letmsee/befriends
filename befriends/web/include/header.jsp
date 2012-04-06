<%-- 
    Document   : header
    Created on : Apr 3, 2012, 12:52:27 AM
    Author     : duongna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>befriends website</title>
    </head>
    <body>
        <table>
            <tr>
                <td>
                    <p>sessionId: ${pageContext.session.id}<br>
                        accountId: ${sessionScope.account.accountId}<br>
                        username: ${sessionScope.account.username}<br>
                        emailAddress: ${sessionScope.account.emailAddress}</p>
                </td>
                <td>
            <li><a href="register_to_web.jsp">register</a></li>
            <li><a href="login.jsp">login</a></li>
            <li><a href="Logout">logout</a>
        </td>
        
    </tr>
</table>

                