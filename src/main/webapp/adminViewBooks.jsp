<%@ page import="com.jaro.webnookbook.BookManager" %>
<%@ page import="com.jaro.webnookbook.Book" %>
<%@ page import="java.util.ArrayList" %>
<%@ page session="true" %>
<%
    if (!"Admin".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }

    ArrayList<Book> books = BookManager.getAllBooks();
%>

<html>
<head>
    <title>Admin - View Books</title>
</head>
<body>
    <h2>Books</h2>

    <table border="1">
        <tr>
            <th>Serial Number</th>
            <th>Title</th>
            <th>Author</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Category</th>
            <th>Actions</th>
        </tr>
        <% for (Book book : books) { %>
            <tr>
                <td><%= book.getSerialNo() %></td>
                <td><%= book.getName() %></td>
                <td><%= book.getAuthor() %></td>
                <td><%= book.getPrice() %></td>
                <td><%= book.getQuantity() %></td>
                <td><%= book.getCategory().getName() %></td>
                <td>
                    <a href="editBook.jsp?id=<%= book.getSerialNo() %>">Edit</a> |
                    <a href="DeleteBookServlet?id=<%= book.getSerialNo() %>" onclick="return confirm('Are you sure?');">Delete</a>
                </td>
            </tr>
        <% } %>
    </table>

    <a href="adminDashboard.jsp">Back to Dashboard</a>
</body>
</html>
