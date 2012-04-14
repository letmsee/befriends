<%-- 
    Document   : view_friend_list
    Created on : Apr 8, 2012, 4:32:47 AM
    Author     : duongna
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/include/header.jsp"/>

<h2><font color="red">DeleteFriend message: ${message} </font></h2>
<h2>Friend List (${totalResults})</h2>
<c:if test="${fn:length(friendList) > 0}">
    
    <table border="1">
        <c:forEach var="acc" items="${friendList}">
            <tr>
                <td>
                    <img src="${acc.avatar}" height="100" width="100" >
                </td>
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
<c:if test="${fn:length(friendList) < totalResults}">
    <form action="ViewFriendList">
        <input type="submit" value="More Results">
        <input type="hidden" name="numOfResults" value="${fn:length(friendList) + incrementOfResults}">
    </form>
</c:if>
<c:import url="/include/footer.jsp"/>