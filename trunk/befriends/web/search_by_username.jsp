<%-- 
    Document   : search_by_username
    Created on : Apr 7, 2012, 11:16:29 AM
    Author     : duongna
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/include/header.jsp"/>
<h3><font color="red">Add friend message: ${message}</font></h3>

<h2>Search result ${fn:length(searchResult)} </h2>
<c:if test="${fn:length(searchResult) == 0}">
    <h3><b>Not found</b></h3>
</c:if>

<c:if test="${fn:length(searchResult) > 0}">    
    <table border="1">
        <c:forEach var="acc" items="${searchResult}">
            <tr>
                <td>
                    <img src="${acc.avatar}" height="100" width="100">
                </td>
                <td>
                    <a href="
                       <c:url value="/ViewPersonalInfo">
                           <c:param name="accountId" value="${acc.accountId}"/>
                       </c:url>
                       ">Username: ${acc.username}</a><br>
                    Age: ${acc.age}<br>
                    Gender: ${acc.gender}<br>
                    School: ${acc.career.school}
                </td>
                <td>
                    <form action="AddFriend">
                        <input type="submit" value="Add Friend">
                        <input type="hidden" name="targetId" value="${acc.accountId}">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:import url="/include/footer.jsp"/>

