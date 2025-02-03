<%-- 
    Document   : register
    Created on : 21 Jan 2025, 19:20:02
    Author     : jaros
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Category</title>
    </head>
    <body>
        <h1>New Category</h1>
        <form action ="newCategory" method = "post">
            <label>Category name</label><br>
            <input type="text" name="categoryName" required>
            <input type="submit" value="insertCategory">
        </form>
        <a href="index.jsp">Home</a>
    </body>
</html>
