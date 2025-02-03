<%-- 
    Document   : logout
    Created on : 1 Feb 2025, 12:16:06
    Author     : jaros
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
<%
    session.invalidate();
    response.sendRedirect("login.jsp");
%>


    </body>
</html>
