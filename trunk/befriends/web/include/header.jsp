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
        <a href="ViewRequestList">View Request List</a><br>
        <a href="ViewWaitingList">View Waiting List</a><br>
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
