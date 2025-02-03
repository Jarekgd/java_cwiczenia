<%@ page import="com.jaro.webnookbook.BookManager" %>
<%@ page import="com.jaro.webnookbook.Book" %>
<%@ page import="java.util.ArrayList" %>
<%@ page session="true" %>
<%
    if (!"Customer".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }

    ArrayList<Book> books = BookManager.getAllBooks();
%>

<html>
<head>
    <title>Available Books</title>
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
            <th>Action</th>
        </tr>
        <% for (Book book : books) { %>
            <tr>
                <td><%= book.getSerialNo() %></td>
                <td><%= book.getName() %></td>
                <td><%= book.getAuthor() %></td>
                <td><%= book.getPrice() %></td>
                <td><%= book.getQuantity() %></td>
                <td>
                    <% if (book.getQuantity() > 0) { %>
                        <form action="AddToCartServlet" method="post">
                            <input type="hidden" name="productType" value="book">
                            <input type="hidden" name="serialNo" value="<%= book.getSerialNo() %>">
                            <button type="submit">Add to Cart</button>
                        </form>
                    <% } else { %>
                        <span style="color: red;">Not Available</span>
                    <% } %>
                </td>
            </tr>
        <% } %>
    </table>

    <a href="customerDashboard.jsp">Back to Dashboard</a>
</body>
</html>
