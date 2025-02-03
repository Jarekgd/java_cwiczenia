<%@ page import="com.jaro.webnookbook.User" %>
<%@ page session="true" %>
<%
    if (!"Admin".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }

    // Retrieve user object from request
    User user = (User) request.getAttribute("user");
    if (user == null) {
        response.sendRedirect("manageUsers.jsp?error=User not found");
        return;
    }
%>

<html>
<head>
    <title>Edit User</title>
</head>
<body>
    <h2>Edit User</h2>

    <form action="EditUserServlet" method="post">
        <!-- User ID (hidden) -->
        <input type="hidden" name="userId" value="<%= request.getAttribute("userId") %>">

        <label>Email:</label>
        <input type="email" name="email" value="<%= user.getEmail() %>" required><br>

        <label>Username:</label>
        <input type="text" name="userName" value="<%= user.getUserName() %>" required><br>

        <label>Login:</label>
        <input type="text" name="login" value="<%= user.getLogin() %>" required><br>

        <label>Role:</label>
        <select name="role">
            <option value="Admin" <% if ("Admin".equals(user.getPrivilege())) { %> selected <% } %>>Admin</option>
            <option value="Customer" <% if ("Customer".equals(user.getPrivilege())) { %> selected <% } %>>Customer</option>
        </select><br>

        <button type="submit">Update User</button>
    </form>

    <a href="manageUsers.jsp">Back to Users</a>
</body>
</html>
