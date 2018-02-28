<%@page import="java.util.List"%>
<%@page import="edu.wctc.dj.week3.model.Name"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Names</title>
	</head>
	<body>
		<table>
			<thead><tr><th>First name</th><th>Last name</th></tr></thead>
			<tbody>
				<%
				List<Name> nameList = (List<Name>) request.getAttribute("nameList");
				if (nameList != null) {
					for (Name name : nameList) {
				%>
						<tr>
							<td><%= name.getFirst() %></td>
							<td><%= name.getLast() %></td>
						</tr>
				<%
					}
				}
				%>
			</tbody>
		</table>

		<table>
			<thead></thead>
			<tbody>
				<c:forEach items="${nameList}" var="name">
					<tr>
						<td><c:out value="${name.first}"/></td>
						<td><c:out value="${name.last}"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</body>
</html>
