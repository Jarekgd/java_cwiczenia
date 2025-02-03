<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
    <h2>Login</h2>
    
    <% 
        String error = request.getParameter("error");
        if (error != null) { 
    %>
        <p style="color:red;">Invalid login or password. Please try again.</p>
    <% 
        } 
    %>

    <form action="LoginServlet" method="post">
        <label>Login:</label>
        <input type="text" name="login" required><br><br>

        <label>Password:</label>
        <input type="password" name="password" required><br><br>

        <input type="submit" value="Login">
    </form>

    <br>
    <a href="register.jsp">Register Here</a>
</body>
</html>
