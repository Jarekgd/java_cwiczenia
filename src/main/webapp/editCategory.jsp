<%@ page import="com.jaro.webnookbook.CategoryManager" %>
<%@ page import="com.jaro.webnookbook.Category" %>
<%@ page session="true" %>
<%
    if (!"Admin".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }

    // Retrieve category ID from request
    String categoryId = request.getParameter("id");
    Category category = CategoryManager.getCategoryById(Integer.parseInt(categoryId));

    if (category == null) {
        response.sendRedirect("manageCategories.jsp?error=Category not found");
        return;
    }
%>

<html>
<head>
    <title>Edit Category</title>
</head>
<body>
    <h2>Edit Category</h2>

    <form action="EditCategoryServlet" method="post">
        <input type="hidden" name="categoryId" value="<%= category.getId() %>">

        <label>Category Name:</label>
        <input type="text" name="categoryName" value="<%= category.getName() %>" required><br>

        <button type="submit">Update Category</button>
    </form>

    <a href="manageCategories.jsp">Back to Categories</a>
</body>
</html>
