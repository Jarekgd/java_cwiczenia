<%-- 
    Document   : register
    Created on : 22 Jan 2025, 10:57:26
    Author     : jaros
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h2>Register</h2>

    <form action="NewUserServlet" method="post">

        <label>Email:</label>
        <input type="email" name="email" required><br><br>

        <label>Username:</label>
        <input type="text" name="userName" required><br><br>

        <label>Login:</label>
        <input type="text" name="login" required><br><br>

        <label>Password:</label>
        <input type="password" name="password" required><br><br>

        <input type="submit" value="Register">
    </form>

    <br>
    <a href="login.jsp">Already have an account? Login</a>
</body>
</html>
