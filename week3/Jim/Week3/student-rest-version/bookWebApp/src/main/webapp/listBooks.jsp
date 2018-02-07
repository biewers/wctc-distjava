<%-- 
    Document   : listAuthors
    Created on : Sep 21, 2015, 9:36:05 PM
    Author     : jlombardo
    Purpose    : display list of author records and (in the future) provide
                 a way to add/edit/delete records
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<fmt:setBundle basename="i18n.MessageBundle" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="book.list.page.title" /></title>
    </head>
    <body>
        <h1><fmt:message key="book.list.page.head1" /></h1>

        <form method="POST" action="BookController?action=addEditDelete">
            <sec:csrfInput />
            
            <sec:authorize access="hasAnyRole('ROLE_MGR')">
            <input type="submit" value="Add/Edit" name="submit" />&nbsp;
            <input type="submit" value="Delete" name="submit" />
            <br><br>
            </sec:authorize>
            
            <table style="width: 75%;" border="1" cellspacing="0" cellpadding="4">
                <tr style="background-color: black;color:white;">
                    <th align="left" class="tableHead">ID</th>
                    <th align="left" class="tableHead">Title</th>
                    <th align="right" class="tableHead">ISBN</th>
                    <th align="right" class="tableHead">Author</th>
                </tr>
                <c:forEach var="b" items="${books}" varStatus="rowCount">
                    <c:choose>
                        <c:when test="${rowCount.count % 2 == 0}">
                            <tr style="background-color: white;">
                            </c:when>
                            <c:otherwise>
                            <tr style="background-color: #ccffff;">
                            </c:otherwise>
                        </c:choose>
                        <td><input type="checkbox" name="bookId" value="${b.bookId}" /></td>
                        <td align="left">${b.title}</td>
                        <td align="left">${b.isbn}</td>
                        <td align="left">
                            <c:choose>
                                <c:when test="${not empty b.authorId}">
                                    ${b.authorId.authorName}
                                </c:when>
                                <c:otherwise>
                                    None
                                </c:otherwise>
                            </c:choose>
                    </td>
                    </tr>
                </c:forEach>
            </table>
            <sec:authorize access="hasAnyRole('ROLE_MGR')">
            <br>
            <input type="submit" value="Add/Edit" name="submit" />&nbsp;
            <input type="submit" value="Delete" name="submit" />
            </sec:authorize>
        </form>
        <p><fmt:message key="page.back.to.home.msg" /> <a href="index.html"><fmt:message key="page.back.to.home.link" /></a></p>

        <c:if test="${errMsg != null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                ${errMsg}</p>
        </c:if>
        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize>
 
    </body>
</html>
