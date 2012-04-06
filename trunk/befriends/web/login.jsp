<%-- 
    Document   : login
    Created on : Apr 5, 2012, 10:13:15 AM
    Author     : duongna
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/include/header.jsp"/>
<p>Input information following:</p>
<p><font color="red">${message}</font></p>
<form action="Login" method="post">
<table>
    <tr>
        <td>Username</td>
        <td><input type="text" name="username" value="${requestScope.account.username}"></td>
    </tr>
    <tr>
        <td>Password</td>
        <td><input type="password" name="password" value="${requestScope.account.password}"</td>
    </tr>
    <tr>
        <td><input type="button" value="login" 
                   onclick="validate(this.form)"></td>
    </tr>
</table>
</form>
<script type="text/javascript">
    function validate(form) {
        if (form.username.value == "") {
            alert("Please input username");
        }
        else if (form.password.value == "")  {
            alert("Please input password");
        } 
        else {
            form.submit();
        }
        
    }
</script>

<c:import url="/include/footer.jsp"/>
