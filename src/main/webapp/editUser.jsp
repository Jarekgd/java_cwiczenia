<%@ page import="com.jaro.webnookbook.UserManager" %>
<%@ page import="com.jaro.webnookbook.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page session="true" %>
<%
    if (!"Admin".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }

    String userId = request.getParameter("id");
    User user = UserManager.getUserById(Integer.parseInt(userId));

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
        <input type="hidden" name="userId" value="<%= user.getId() %>">

        <label>Email:</label>
        <input type="email" name="email" value="<%= user.getEmail() %>" required><br>

        <label>Username:</label>
        <input type="text" name="userName" value="<%= user.getUserName() %>" required><br>

        <label>Login:</label>
        <input type="text" name="login" value="<%= user.getLogin() %>" required><br>

        <label>Role:</label>
        <select name="role">
            <option value="Admin" <% if ("Admin".equals(user.getRole())) { %> selected <% } %>>Admin</option>
            <option value="Customer" <% if ("Customer".equals(user.getRole())) { %> selected <% } %>>Customer</option>
        </select><br>

        <button type="submit">Update User</button>
    </form>

    <a href="manageUsers.jsp">Back to Users</a>
</body>
</html>
