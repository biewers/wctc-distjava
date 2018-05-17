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
        <title>Author List (Ajax)</title>
        <link href="resources/css/app.css" rel="stylesheet" type="text/css"/>
    </head>
    <body class="authorList">
        <h1>Author List (Ajax)</h1>
        <p style="width: 50%;">Note that only the table rows, after the header, are updated. There 
            is no complete page refresh because we're using Ajax to get the 
            data in JSON format from the server and then use client-side 
            JavaScript to take that data and append rows to the table.
        </p>

        <table id="authorListTable" style="width: 50%;" border="1" cellspacing="0" cellpadding="4">
            <thead>
                <tr style="background-color: black;color:white;">
                    <th align="left" class="tableHead">ID</th>
                    <th align="left" class="tableHead">Author Name</th>
                    <th align="right" class="tableHead">Date Added</th>
                </tr>
            </thead>
            <tbody id="authorTblBody">
                
            </tbody>
        </table>
        
        <table id="addEditAuthor" style="display:none;" width="500" border="1" cellspacing="0" cellpadding="4">
            <tr>
                <td style="background-color: black;color:white;" align="left">ID</td>
                <td align="left"><input type="text" value="" id="authorId" name="authorId" readonly/></td>
            </tr>         
            <tr>
                <td style="background-color: black;color:white;" align="left">Name</td>
                <td align="left"><input type="text" value="" id="authorName" name="authorName" /></td>
            </tr>
            <tr>
                <td style="background-color: black;color:white;" align="left">Date Added</td>
                <td align="left"><input type="text" value="" id="dateAdded" name="dateAdded" readonly /></td>
            </tr>         
        </table>

        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize>   


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" type="text/javascript"></script>
        <script src="resources/js/app.js" type="text/javascript"></script>
    </body>
</html>
