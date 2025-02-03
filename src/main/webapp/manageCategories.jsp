<%@ page import="java.util.ArrayList" %>
<%@ page import="com.jaro.webnookbook.Category" %>
<%@ page import="com.jaro.webnookbook.CategoryManager" %>

<%@ page session="true" %>
<%
    if (!"Admin".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }
%>

<html>
<head>
    <title>Manage Categories</title>
</head>
<body>
    <h2>Manage Categories</h2>
    <a href="addCategory.jsp">Add New Category</a>

    <h3>Existing Categories</h3>
    <%
        ArrayList<Category> categories = CategoryManager.getAllCategories();
        for (Category category : categories) {
    %>
        <p>
            <b>Category Name:</b> <%= category.getName() %><br>
            <a href="editCategory.jsp?id=<%= category.getId() %>">Edit</a> | 
            <a href="deleteCategoryServlet?id=<%= category.getId() %>" onclick="return confirm('Are you sure?');">Delete</a>
        </p>
        <hr>
    <% } %>

    <a href="adminDashboard.jsp">Back</a>
</body>
</html>
