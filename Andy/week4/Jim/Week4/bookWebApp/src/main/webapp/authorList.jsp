<%-- 
    Document   : authorList
    Created on : Feb 8, 2016, 9:37:48 AM
    Author     : jlombardo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Author List</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    </head>
    <body>
        <!-- 
            Wrapping content in a Bootstrap container div provides a
            variety of benefits including margins and fluid design for
            other form factors.
        -->
        <div class="container">
            <h2>Author List</h2>
            <table style="width: 500px;" class="table table-striped table-hover table-condensed">
                <tr style="background-color: black;color:white;">
                    <th align="left" class="tableHead">ID</th>
                    <th align="left" class="tableHead">Author Name</th>
                    <th align="right" class="tableHead">Date Added</th>
                </tr>
                <c:forEach var="a" items="${authors}">
                    <tr>
                        <td align="left">${a.authorId}</td>
                        <td align="left">${a.authorName}</td>
                        <td align="right">
                            <fmt:formatDate pattern="M/d/yyyy" value="${a.dateAdded}"></fmt:formatDate>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            
            <p><a href="index.html">Return to Main Menu</a></p>
        
            <c:if test="${errMsg != null}">
                <p style="font-weight: bold;color: red;width:500px;">${errMsg}</p>
            </c:if>

        </div>
        <!-- end container -->
        
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </body>
</html>