<%-- 
    Document   : welcome
    Created on : Apr 14, 2012, 11:16:58 PM
    Author     : duongna
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/include/header.jsp" />
<table border="1">
    <tr>
        <td>
            <img src="${sessionScope.account.avatar}" height="200" width="200">
        </td>
        <td>
            Username: ${sessionScope.account.username}
            Email Address: ${sessionScope.account.emailAddress}
        </td>
    </tr>
</table>

<%-- Recommend some appropriate people --%>
<c:if test="${fn:length(searchResult) > 0}">
    <table border="1">
        <h2>Some people use may like</h2>
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
    <a href="/SearchMatch">More...</a>
</c:if>

<%-- rank new register accounts --%>

<c:if test="${fn:length(rankNewResult) > 0}">
    <table border="1">
        <h2>System has some new people use may like</h2>
        <c:forEach var="acc" items="${rankNewResult}">
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
<c:import url="/include/footer.jsp" />