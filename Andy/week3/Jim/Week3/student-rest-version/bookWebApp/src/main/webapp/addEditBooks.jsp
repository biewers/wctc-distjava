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
        <title>Add/Edit Book</title>
    </head>
    <body>
        <h1>Add/Edit Book</h1>

        <form method="POST" action="BookController">
            <sec:csrfInput />
            
            <sec:authorize access="hasAnyRole('ROLE_MGR')">
            <table width="500" border="1" cellspacing="0" cellpadding="4">
                <!--
                    In the EL expression below using 'not empty' is better than
                    author != null because it tests for both null and empty string
                -->
                <c:choose>
                    <c:when test="${not empty book}">
                        <tr>
                            <td style="background-color: black;color:white;" align="left">ID</td>
                            <td align="left"><input type="text" value="${book.bookId}" name="bookId" readonly/></td>
                        </tr>         
                    </c:when>
                </c:choose>
                        
                <tr>
                    <td style="background-color: black;color:white;" align="left">Title</td>
                    <td align="left"><input type="text" value="${book.title}" name="title" /></td>
                </tr>
                <tr>
                    <td style="background-color: black;color:white;" align="left">ISBN</td>
                    <td align="left"><input type="text" value="${book.isbn}" name="isbn" /></td>
                </tr>

                <tr>
                    <td style="background-color: black;color:white;" align="left">Author</td>
                    <td align="left">
                    <select id="authorDropDown" name="authorId">
                    <c:choose>
                        <c:when test="${not empty book.authorId}">
                            <option value="">None</option>
                            <c:forEach var="author" items="${authors}">                                       
                                <option value="${author.authorId}" <c:if test="${book.authorId.authorId == author.authorId}">selected</c:if>>${author.authorName}</option>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="author" items="${authors}" varStatus="rowCount">                                       
                                <option value="${author.authorId}" <c:if test="${rowCount.count == 1}">selected</c:if>>${author.authorName}</option>
                            </c:forEach>
                         </c:otherwise>
                    </c:choose>
                    </select>
                </td>
                </tr>

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
