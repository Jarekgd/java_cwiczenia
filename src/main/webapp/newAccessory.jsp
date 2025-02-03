<%-- 
    Document   : newAccessory
    Created on : 22 Jan 2025, 14:51:43
    Author     : jaros
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New accessory</title>
    </head>
    <body>
        <h1>New accessory</h1>
        <form action ="newAccessory" method = "post">
            <label>Category name</label><br>
            <input type="text" name="serialNo" required><br>
            <label>Accessory name</label><br>
            <input type="text" name="accessoryName" required><br>
            <label>Price:</label><br>
            <input type="text" name="price" required><br>
            <label>Quantity</label><br>
            <input type="text" name="quantity" required><br>
            <input type="submit" value="Submit">
        </form>
        <a href="index.jsp">Home</a>
    </body>
</html>
