<%-- 
    Document   : change_personal_info
    Created on : Apr 13, 2012, 9:53:35 AM
    Author     : duongna
--%>

<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="business.Account"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/include/header.jsp" />

<c:if test="${message != null}">
    <h2><font color="red">${message}</font></h2>
</c:if>

<img src="${account.avatar}" height="200" width="200"> 

<form action="ChangePersonalInfo" method="post">

<h2>Basic info</h2>
<table>
    <tr>
        <td>Avatar url:</td>
        <td><input type="text" name="avatar" value="${account.avatar}"></td>
    </tr>
    <tr>
        <td>Username:</td>
        <td>${account.username}</td>
    </tr>
    <tr>
        <td>Email Address:</td>
        <td>${account.emailAddress}</td>
    </tr>
    <tr>
        <%-- display birthday --%>
        <td>Birthday</td>
        <%
               Account acc = (Account) request.getAttribute("account");
               Date birthday = (Date) acc.getBirthday();
               log("birthday:" + birthday);

               SimpleDateFormat formater = new SimpleDateFormat("dd");
               int day = Integer.parseInt(formater.format(birthday) );
               
               formater = new SimpleDateFormat("MM");
               int month = Integer.parseInt(formater.format(birthday));

               formater = new SimpleDateFormat("yyyy");
               int year = Integer.parseInt(formater.format(birthday));
               
               request.setAttribute("_day", day);
               request.setAttribute("_month", month);
               request.setAttribute("_year", year);
               log("_day:" + day + " _month:" + month + " _year:" + year);
        %>
        <td>
            <select name="day">
                <c:forEach begin="1" end="31" varStatus="status">
                    <c:choose>
                        <c:when test="${status.index == _day}">
                            <option selected>${status.index}</option>
                        </c:when>
                        <c:otherwise>
                            <option >${status.index}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
            
            <select name="month" >
                <c:forEach begin="1" end="12" varStatus="status">
                    <c:choose>
                        <c:when test="${status.index == _month}">
                            <option selected>${status.index}</option>
                        </c:when>
                        <c:otherwise>
                            <option >${status.index}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
            
            <select name="year">
                <option>Year</option>
                <c:forEach begin="1980" end="2012" varStatus="status">
                    <c:choose>
                        <c:when test="${status.index == _year}">
                            <option selected>${status.index}</option>
                        </c:when>
                        <c:otherwise>
                            <option >${status.index}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </td>   
        
    </tr>
    <tr>
        <%-- display gender --%>
        <td>Gender</td>
        <td>
            <select name="gender">
                <c:choose>
                    <c:when test="${account.gender == 'female'}">
                        <option selected>female</option>
                    </c:when>
                    <c:otherwise>
                        <option>female</option>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${account.gender == 'male'}">
                        <option selected>male</option>
                    </c:when>
                    <c:otherwise>
                        <option>male</option>
                    </c:otherwise>
                </c:choose>
            </select>
        </td>
    </tr>
    <tr>
        <%-- Display friend gender --%>
        <td>Friend Gender</td>
        <td>
            <select name="interestGender">
                <c:choose>
                    <c:when test="${account.interestGender == 'female'}">
                        <option selected>female</option>
                    </c:when>
                    <c:otherwise>
                        <option>female</option>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${account.interestGender == 'male'}">
                        <option selected>male</option>
                    </c:when>
                    <c:otherwise>
                        <option>male</option>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${account.interestGender == 'both'}">
                        <option selected>both</option>
                    </c:when>
                    <c:otherwise>
                        <option>both</option>
                    </c:otherwise>
                </c:choose>
            </select>
        </td>
    </tr>
</table>

<h2>Hobbies and Dislikes</h2>

<%
           HashMap<String, String> map = new HashMap<String, String>();
%>
<c:forEach var="hobby" items="${account.hobbies}">
    <c:choose>
        <c:when test="${hobby == 'music'}">
            <% map.put("music", "like"); 
               System.out.println("music is putted");
            %>
        </c:when>
        <c:when test="${hobby == 'movie'}">
            <% map.put("movie", "like"); %>
        </c:when>
        <c:when test="${hobby == 'book'}">
            <% map.put("book", "like"); %>
        </c:when>
        <c:when test="${hobby == 'sport'}">
            <% map.put("sport", "sport"); %>
        </c:when>
        <c:when test="${hobby == 'game'}">
            <% map.put("game", "like"); %>
        </c:when>
    </c:choose>
</c:forEach>

<c:forEach var="dislike" items="${account.dislikes}">
    <c:choose>
        <c:when test="${dislike == 'music'}">
            <% map.put("music", "dislike"); %>
        </c:when>
        <c:when test="${dislike == 'movie'}">
            <% map.put("movie", "dislike"); %>
        </c:when>
        <c:when test="${dislike == 'book'}">
            <% map.put("book", "dislike"); %>
        </c:when>
        <c:when test="${dislike == 'sport'}">
            <% map.put("sport", "dislike"); %>
        </c:when>
        <c:when test="${dislike == 'game'}">
            <% map.put("game", "dislike"); %>
        </c:when>
    </c:choose>
</c:forEach>

<%
request.setAttribute("_map", map);
%>

<table border="1">
    <tr>
        <td>Music</td>
        <td>
            <c:choose>
                <c:when test="${_map.music =='like'}">
                    <input type="radio" name="music" value ="like" checked>Like
                    <input type="radio" name="music" value="dislike">Dislike
                    <input type="radio" name="music" value="no idea">No idea
                </c:when>
                <c:when test="${_map.music == 'dislike'}">
                    <input type="radio" name="music" value ="like">Like
                    <input type="radio" name="music" value="dislike" checked>Dislike
                    <input type="radio" name="music" value="no idea">No idea
                </c:when>
                <c:otherwise>
                    <input type="radio" name="music" value="like">Like
                    <input type="radio" name="music" value="dislike">Dislike
                    <input type="radio" name="music" value="no idea" checked>No idea
                </c:otherwise>
            </c:choose>
        </td>
        
    <tr>
        <td>Movie</td>
        <td>
            <c:choose>
                <c:when test="${_map.movie == 'like'}">
                    <input type="radio" name="movie" value="like" checked>Like
                    <input type="radio" name="movie" value="dislike">Dislike
                    <input type="radio" name="movie" value="no idea">No idea
                </c:when>
                <c:when test="${_map.movie == 'dislike'}">
                    <input type="radio" name="movie" value="like">Like
                    <input type="radio" name="movie" value="dislike" checked>Dislike
                    <input type="radio" name="movie" value="no idea">No idea
                </c:when>
                <c:otherwise>
                    <input type="radio" name="movie" value="like">Like
                    <input type="radio" name="movie" value="dislike">Dislike
                    <input type="radio" name="movie" value="no idea" checked>No idea
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    
    <tr>
        <td>Book</td>
        <td>
            <c:choose>
                <c:when test="${_map.book == 'like'}">
                    <input type="radio" name="book" value="like" checked>Like
                    <input type="radio" name="book" value="dislike">Dislike
                    <input type="radio" name="book" value="no idea">No idea
                </c:when>
                <c:when test="${_map.movie == 'dislike'}">
                    <input type="radio" name="book" value="like">Like
                    <input type="radio" name="book" value="dislike" checked>Dislike
                    <input type="radio" name="book" value="no idea">No idea
                </c:when>
                <c:otherwise>
                    <input type="radio" name="book" value="like">Like
                    <input type="radio" name="book" value="dislike">Dislike
                    <input type="radio" name="book" value="no idea" checked>No idea
                 </c:otherwise>
            </c:choose>
        </td>
    </tr>
    
    <tr>
        <td>Sport</td>
        <td>
            <c:choose>
                <c:when test="${_map.sport == 'like'}">
                    <input type="radio" name="sport" value="like" checked>Like
                    <input type="radio" name="sport" value="dislike">Dislike
                    <input type="radio" name="sport" value="no idea">No idea
                </c:when>
                <c:when test="${_map.sport == 'dislike'}">
                    <input type="radio" name="sport" value="like">Like
                    <input type="radio" name="sport" value="dislike" checked>Dislike
                    <input type="radio" name="sport" value="no idea">No idea
                </c:when>
                <c:otherwise>
                   <input type="radio" name="sport" value="like">Like
                    <input type="radio" name="sport" value="dislike">Dislike
                    <input type="radio" name="sport" value="no idea" checked>No idea
                </c:otherwise>
            </c:choose>
        </td>
    </tr>

    <tr>
        <td>Game</td>
        <td>
            <c:choose>
                <c:when test="${_map.game == 'like'}">
                    <input type="radio" name="game" value="like" checked>Like
                    <input type="radio" name="game" value="dislike">Dislike
                    <input type="radio" name="game" value="no idea">No idea
                </c:when>
                <c:when test="${_map.game == 'dislike'}">
                    <input type="radio" name="game" value="like">Like
                    <input type="radio" name="game" value="dislike" checked>Dislike
                    <input type="radio" name="game" value="no idea">No idea
                </c:when>
                <c:otherwise>
                    <input type="radio" name="game" value="like">Like
                    <input type="radio" name="game" value="dislike">Dislike
                    <input type="radio" name="game" value="no idea" checked>No idea
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>

<h2>Study and Job</h2>
<table border="1">
    <tr>
        <td>School:</td>
        <td><input type="text" name="school" value="${account.career.school}"></td>
    </tr>
    <tr>
        <td>Job</td>
        <td><input type="text" name="job" value="${account.career.job}"></td>
    </tr>
</table>

<h2>Residence</h2> 
<table border="1">
    <tr>
        <td>Address:</td>
        <td><input type="text" name="address" value="${account.location.address}"></td>
    </tr>
    <tr>
        <td>Country:</td>
        <td><input type="text" name="country" value="${account.location.country}"></td>
    </tr>
    <tr>
        <td>Home town:</td>
        <td><input type="text" name="hometown" value="${account.location.hometown}"></td>
    </tr>
</table> 
    
    <input type="submit" value="Save changes">
</form>
        <c:import url="/include/footer.jsp" />