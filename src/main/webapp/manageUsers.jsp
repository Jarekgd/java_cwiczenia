<%@ page import="java.util.ArrayList" %>
<%@ page import="com.jaro.webnookbook.User" %>
<%@ page import="com.jaro.webnookbook.UserManager" %>

<%@ page session="true" %>
<%
    if (!"Admin".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }
%>

<html>
<head>
    <title>Manage Users</title>
</head>
<body>
    <h2>Manage Users</h2>

    <h3>All Users (Admins & Customers)</h3>
    <%
        ArrayList<User> users = UserManager.getAllUsers();
        for (User user : users) {
    %>
        <p>
            <b>Email:</b> <%= user.getEmail() %><br>
            <b>Username:</b> <%= user.getUserName() %><br>
            <b>Login:</b> <%= user.getLogin() %><br>
            <b>Role:</b> <%= user.getPrivilege() %> <br>
            <% if (!"Admin".equals(user.getPrivilege())) { %>
                <a href="editUser.jsp?id=<%= user.getLogin() %>">Edit</a> | 
                <a href="deleteUserServlet?id=<%= user.getLogin() %>" onclick="return confirm('Are you sure?');">Delete</a>
            <% } %>
        </p>
        <hr>
    <% } %>

    <a href="adminDashboard.jsp">Back</a>
</body>
</html>
