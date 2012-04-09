<%-- 
    Document   : view_friend_list
    Created on : Apr 8, 2012, 4:32:47 AM
    Author     : duongna
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/include/header.jsp"/>

<h2><font color="red">DeleteFriend message: ${message} </font></h2>
<h2>Waiting List (${fn:length(friendList)})</h2>
<c:if test="${fn:length(friendList) > 0}">
    
    <table border="1">
        <c:forEach var="acc" items="${friendList}">
            <tr>
                <td>
                    Username: ${acc.username}<br>
                    Age: ${acc.age}<br>
                    Gender: ${acc.gender}<br>
                    School: ${acc.career.school}
                </td>
                <td>
                    <form action="DeleteFriend">
                        <input type="submit" value="Delete">
                        <input type="hidden" name="friendId" value="${acc.accountId}">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    
</c:if>
<c:import url="/include/footer.jsp"/>