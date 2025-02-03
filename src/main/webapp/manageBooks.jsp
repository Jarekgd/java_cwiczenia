<%@ page import="java.util.ArrayList" %>
<%@ page import="com.jaro.webnookbook.Book" %>
<%@ page import="com.jaro.webnookbook.BookManager" %>

<%@ page session="true" %>
<%
    if (!"Admin".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }
%>

<html>
<head>
    <title>Manage Books</title>
</head>
<body>
    <h2>Manage Books</h2>
    <a href="addBook.jsp">Add New Book</a>

    <h3>Existing Books</h3>
    <%
        ArrayList<Book> books = BookManager.getAllBooks();
        for (Book book : books) {
    %>
        <p>
            <b>Title:</b> <%= book.getName() %><br>
            <b>Author:</b> <%= book.getAuthor() %><br>
            <b>Price:</b> $<%= book.getPrice() %><br>
            <a href="editBook.jsp?id=<%= book.getSerialNo() %>">Edit</a> | 
            <a href="deleteBookServlet?id=<%= book.getSerialNo() %>" onclick="return confirm('Are you sure?');">Delete</a>
        </p>
        <hr>
    <% } %>

    <a href="adminDashboard.jsp">Back</a>
</body>
</html>
