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

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add/Edit Author</title>
    </head>
    <body>
        <h1>Add/Edit Author</h1>

        <form method="POST" action="AuthorController">
            <sec:csrfInput />
            
            <sec:authorize access="hasAnyRole('ROLE_MGR')">
            <table width="500" border="1" cellspacing="0" cellpadding="4">
                <!--
                    In the EL expression below using 'not empty' is better than
                    author != null because it tests for both null and empty string
                -->
                <c:choose>
                    <c:when test="${not empty author}">
                        <tr>
                            <td style="background-color: black;color:white;" align="left">ID</td>
                            <td align="left"><input type="text" value="${author.authorId}" name="authorId" readonly/></td>
                        </tr>         
                    </c:when>
                </c:choose>

                <c:choose>
                    <c:when test="${not empty author.bookSet}">
                        <tr>
                            <td style="background-color: black;color:white;" align="left">Books</td>
                            <td>
                                <select id="booksDropDown" name="bookId">
                                    <c:forEach var="book" items="${author.bookSet}" varStatus="rowCount">                                       
                                        <option value="${book.bookId}">${book.title}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td style="background-color: black;color:white;" align="left">Books</td>
                            <td>None</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                <tr>
                    <td style="background-color: black;color:white;" align="left">Name</td>
                    <td align="left"><input type="text" value="${author.authorName}" name="authorName" /></td>
                </tr>

                <c:choose>
                    <c:when test="${not empty author}">
                        <tr>
                            <td style="background-color: black;color:white;" align="left">Date Added</td>
                            <td align="left"><input type="text" value="<fmt:formatDate pattern='yyyy-MM-dd' value='${author.dateAdded}'/>" name="dateAdded" readonly /></td>
                        </tr>         
                    </c:when>
                </c:choose>

                <tr>
                <input type="submit" value="Cancel" name="action" />&nbsp;
                <input type="submit" value="Save" name="action" />
                </tr>
            </table>
                </sec:authorize>
        </form>
        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize>
    </body>
</html>
