<%@ page session="true" %>
<%@ page import="java.sql.*" %>

<%
    // Redirect if not logged in
    if (session.getAttribute("userLogin") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String login = (String) session.getAttribute("userLogin");
    String dbUrl = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";
    
    String email = "";
    String userName = "";
    String password = "";

    try {
        Class.forName("org.sqlite.JDBC");
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            String sql = "SELECT email, userName, password FROM users WHERE login = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, login);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        email = rs.getString("email");
                        userName = rs.getString("userName");
                        password = rs.getString("password");
                    }
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<html>
<head>
    <title>Edit Account</title>
</head>
<body>
    <h2>Edit Account Details</h2>

    <% if (request.getParameter("error") != null) { %>
        <p style="color:red;">Error updating account. Please try again.</p>
    <% } %>

    <% if (request.getParameter("success") != null) { %>
        <p style="color:green;">Account updated successfully!</p>
    <% } %>

    <form action="EditAccountServlet" method="post">
        <label>Email:</label>
        <input type="email" name="email" value="<%= email %>" required><br><br>

        <label>Username:</label>
        <input type="text" name="userName" value="<%= userName %>" required><br><br>

        <label>Login:</label>
        <input type="text" name="login" value="<%= login %>" required><br><br>

        <label>Password:</label>
        <input type="password" name="password" value="<%= password %>" required><br><br>

        <input type="submit" value="Update">
    </form>

    <a href="customerDashboard.jsp">Back</a>
</body>
</html>
