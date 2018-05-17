<%-- 
    Document   : nameDetail
    Created on : Feb 6, 2018, 8:25:05 PM
    Author     : andrewbiewer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>JSP Page</title>
	</head>
	<body>
		<h1>Name Detail!</h1>
		<table>
			<tr>
				<td>First</td>
				<td><c:out value="${name.first}"/></td>
			</tr>
			<tr>
				<td>Last</td>
				<td><c:out value="${name.last}"/></td>
			</tr>
			<tr>
				<td>Street</td>
				<td><c:out value="${name.address.street}"/></td>
			</tr>
			<tr>
				<td>City</td>
				<td><c:out value="${name.address.city}"/></td>
			</tr>
			<tr>
				<td>State</td>
				<td><c:out value="${name.address.state}"/></td>
			</tr>
			<tr>
				<td>Zip</td>
				<td><c:out value="${name.address.zip}"/></td>
			</tr>
		</table>
	</body>
</html>
