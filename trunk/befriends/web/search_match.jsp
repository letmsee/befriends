<%-- 
    Document   : search_match
    Created on : Apr 13, 2012, 1:49:52 PM
    Author     : duongna
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/include/header.jsp" />

<h2>Search result ${totalResults} </h2>
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
                    Common Friend(s): ${acc.numOfCommonFriends}<br>
                    Match score: ${acc.matchScore}
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
<c:if test="${fn:length(searchResult) < totalResults}">
    <form action="SearchByUsername">
        <input type="submit" value="More Results">
        <input type="hidden" name="numOfResults" value="${fn:length(searchResult) + incrementOfResults}">
        <input type="hidden" name="usernameToFind" value="${usernameToFind}">
    </form>
</c:if>
<c:import url="/include/footer.jsp" />
    