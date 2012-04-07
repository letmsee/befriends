<%-- 
    Document   : search_by_username
    Created on : Apr 7, 2012, 11:16:29 AM
    Author     : duongna
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/include/header.jsp"/>
<h2>Search result ${fn:length(searchResult)} </h2>
<c:if test="${fn:length(searchResult) == 0}">
    <h3><b>Not found</b></h3>
</c:if>

<c:if test="${fn:length(searchResult) > 0}">    
    <table border="1">
        <c:forEach var="acc" items="${searchResult}">
            <tr>
                <td>
                    Username: ${acc.username}<br>
                    Age: ${acc.age}<br>
                    School: ${acc.school}
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:import url="/include/footer.jsp"/>

