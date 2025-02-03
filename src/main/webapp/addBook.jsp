<%@ page import="java.util.ArrayList" %>
<%@ page import="com.jaro.webnookbook.Category" %>
<%@ page import="com.jaro.webnookbook.CategoryManager" %>
<%@ page session="true" %>
<%
    if (!"Admin".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }

    ArrayList<Category> categories = CategoryManager.getAllCategories();
%>

<html>
<head>
    <title>Add Book</title>
</head>
<body>
    <h2>Add New Book</h2>

    <form action="AddBookServlet" method="post">
        <label>Serial Number:</label>
        <input type="text" name="serialNo" required><br>

        <label>Book Name:</label>
        <input type="text" name="bookName" required><br>

        <label>Author:</label>
        <input type="text" name="author" required><br>

        <label>Price:</label>
        <input type="number" step="0.01" name="bookPrice" required><br>

        <label>Quantity:</label>
        <input type="number" name="bookQuantity" required><br>

        <label>Category:</label>
        <select name="categoryId" required>
            <% for (Category category : categories) { %>
                <option value="<%= category.getId() %>"><%= category.getName() %></option>
            <% } %>
        </select><br>

        <button type="submit">Add Book</button>
    </form>

    <a href="manageBooks.jsp">Back to Books</a>
</body>
</html>
