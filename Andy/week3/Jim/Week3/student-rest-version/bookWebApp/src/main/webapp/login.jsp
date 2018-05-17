<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="Jim Lombardo">

        <title>Spring Security Login Page</title>
        <link rel="stylesheet" href="css/styles.css" /> 
    </head>

    <body>
       
        <form id="signInForm" role="form" method='POST' action="<c:url value='j_spring_security_check' />">
            <sec:csrfInput />
            
            
            <div class="col-sm-6">
                <h3 style="font-weight: 200;">Sign in </h3>
                <div class="form-group">
                    <input tabindex="1" class="form-control" id="j_username" name="j_username" placeholder="Email address" type="text" autofocus />
                    <input tabindex="2" class="form-control" id="j_password" name="j_password" type="password" placeholder="password" />
                </div>
                <div class="form-group">
                    <input class="btn btn-warning" name="submit" type="submit" value="Sign in" />
                </div>
            </div>
        </form>


    </body>
</html>