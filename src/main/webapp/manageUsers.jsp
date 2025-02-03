<%@ page import="java.util.ArrayList" %>
<%@ page import="com.jaro.webnookbook.User" %>
<%@ page import="com.jaro.webnookbook.UserManager" %>
<%@ page session="true" %>
<%
    if (!"Admin".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }

    ArrayList<User> users = UserManager.getAllUsers();
%>

<html>
<head>
    <title>Manage Users</title>
</head>
<body>
    <h2>All Users (Admins & Customers)</h2>
    <table border="1">
        <tr>
            <th>Email</th>
            <th>Username</th>
            <th>Login</th>
            <th>Role</th>
            <th>Actions</th>
        </tr>
        <% for (User user : users) { %>
        <tr>
            <td><%= user.getEmail() %></td>
            <td><%= user.getUserName() %></td>
            <td><%= user.getLogin() %></td>
            <td><%= user.getPrivilege() %></td>
            <td>
                <a href="editUser.jsp?id=<%= user.getLogin() %>">Edit</a> |
                <a href="DeleteUserServlet?id=<%= user.getLogin() %>" onclick="return confirm('Are you sure?');">Delete</a>
            </td>
        </tr>
        <% } %>
    </table>
</body>
</html>
