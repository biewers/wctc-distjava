<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <p><a href="TestController?action=random">Click here for random message</a></p>
        <p><a href="TestController?action=list">Click here for list of messages</a></p>
        
        <h3>Results:</h3>
        <p>${msg}</p>
        <ul>
        <c:forEach var="item" items="${msgList}">
            <li>${item}</li>
        </c:forEach>
        </ul>
    </body>
</html>
