<%-- 
    Document   : view_request_list
    Created on : Apr 7, 2012, 9:59:17 PM
    Author     : duongna
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/include/header.jsp"/>
<h2><font color="red">message: ${message}</font></h2>
<h2>Request List (${fn:length(requestList)})</h2>
<c:if test="${fn:length(requestList) > 0}">
    
    <table border="1">
        <c:forEach var="acc" items="${requestList}">
            <tr>
                <td>
                    Username: ${acc.username}<br>
                    Age: ${acc.age}<br>
                    Gender: ${acc.gender}<br>
                    School: ${acc.career.school}
                </td>
                <td>
                    <form action="AcceptRequest">
                        <input type="submit" value="accept">
                        <input type="hidden" name="requestId" value="${acc.accountId}">
                    </form>
                </td>
                <td>
                    <form action="DenyRequest">
                        <input type="submit" value="deny">
                        <input type="hidden" name="requestId" value="${acc.accountId}">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    
</c:if>
<c:import url="/include/footer.jsp"/>