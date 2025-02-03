<%@ page import="com.jaro.webnookbook.BookManager" %>
<%@ page import="com.jaro.webnookbook.Book" %>
<%@ page import="com.jaro.webnookbook.CategoryManager" %>
<%@ page import="com.jaro.webnookbook.Category" %>
<%@ page import="java.util.ArrayList" %>
<%@ page session="true" %>
<%
    if (!"Admin".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }

    String serialNo = request.getParameter("id");
    Book book = BookManager.getBookBySerialNo(serialNo);
    ArrayList<Category> categories = CategoryManager.getAllCategories();

    if (book == null) {
        response.sendRedirect("manageBooks.jsp?error=Book not found");
        return;
    }
%>

<html>
<head>
    <title>Edit Book</title>
</head>
<body>
    <h2>Edit Book</h2>

    <form action="EditBookServlet" method="post">
        <input type="hidden" name="serialNo" value="<%= book.getSerialNo() %>">

        <label>Book Title:</label>
        <input type="text" name="title" value="<%= book.getName() %>" required><br>
 
        <label>Author:</label>
        <input type="text" name="author" value="<%= book.getAuthor() %>" required><br>       

        <label>Price:</label>
        <input type="number" step="0.01" name="bookPrice" value="<%= book.getPrice() %>" required><br>

        <label>Quantity:</label>
        <input type="number" name="bookQuantity" value="<%= book.getQuantity() %>" required><br>

        <label>Category:</label>
        <select name="categoryId" required>
            <% for (Category category : categories) { %>
                <option value="<%= category.getId() %>" <% if (category.getId() == book.getCategoryId()) { %> selected <% } %>>
                    <%= category.getName() %>
                </option>
            <% } %>
        </select><br>

        <button type="submit">Update Book</button>
    </form>

    <a href="manageBooks.jsp">Back</a>
</body>
</html>
