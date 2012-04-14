<%-- 
    Document   : register_to_web
    Created on : Apr 3, 2012, 1:01:14 AM
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/include/header.jsp"/>

<h3>${message}</h3>
<p>Please input information following:</p>
<form action="RegisterToWeb" method="post">
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
            <td>Email address</td>
            <td><input type="text" name="emailAddress" value="${requestScope.account.emailAddress}"</td>
        </tr>
        <tr>
            <td>Gender</td>
            <td>
                <input type="radio" name="gender" value="male" checked>Male
                <input type="radio" name="gender" value="female">Female
            </td>
        </tr>
        <tr>
            <td>Birthday</td>
            <td>
                 <select name="day">
                    <option>Day</option>
                    <c:forEach begin="1" end="31" varStatus="status">
                        <option>${status.index}</option>
                    </c:forEach>
                </select>
                <select name="month" >
                    <option>Month</option>
                    <c:forEach begin="1" end="12" varStatus="status">
                        <option>${status.index}</option>
                    </c:forEach>
                </select>
                <select name="year">
                    <option>Year</option>
                    <c:forEach begin="1980" end="2012" varStatus="status">
                        <option>${status.index}</option>
                    </c:forEach>
                </select>
            </td>                
        </tr>
        
        <tr>
            <td><input type="button" value="Submit"
                       onclick="validate(this.form)"></td>
        </tr>
    </table>
</form>

<script type="text/javascript">
     function validate(form) {
         if (form.username.value == "") {
             alert("Please fill Username");
         }
         else if (form.password.value == "") {
             alert("Please fill Password");
         }
         else if (form.emailAddress.value == "") {
             alert("Please fill Email address");
         }
         else if (form.day.value == "Day" || form.month.value == "Month" ||
             form.year.value == "Year") {
             alert("Please choose birthday"); 
         } 
         else {
             form.submit();
         }
     } 
</script> 

<c:import url="/include/footer.jsp"/>
