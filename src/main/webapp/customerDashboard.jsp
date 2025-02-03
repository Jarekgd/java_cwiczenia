<%@ page import="java.util.ArrayList" %>
<%@ page import="com.jaro.webnookbook.Book" %>
<%@ page import="com.jaro.webnookbook.BookManager" %>
<%@ page import="com.jaro.webnookbook.Accessory" %>
<%@ page import="com.jaro.webnookbook.AccessoryManager" %>

<%@ page session="true" %>
<%
    // Redirect to login page if the user is not a customer
    if (!"Customer".equals(session.getAttribute("userRole"))) {     
        response.sendRedirect("login.jsp"); 
    }
%>

<html>
<head>
    <title>Customer Dashboard</title>
</head>
<body>
    <h2>Welcome, <%= session.getAttribute("userLogin") %></h2>

    <h3>Options</h3>
    <ul>
        <li><a href="viewBooks.jsp">Show Books</a></li>
        <li><a href="searchBooks.jsp">Search Books</a></li>
        <li><a href="viewAccessories.jsp">Show Accessories</a></li>
        <li><a href="shoppingCart.jsp">View Shopping Cart</a></li>
        <li><a href="editAccount.jsp">Modify Account Details</a></li>
        <li><a href="logout.jsp">Logout</a></li>
    </ul>

    <h3>Available Books</h3>
    <%
        ArrayList<Book> books = BookManager.getAllBooks();
        for (Book book : books) {
    %>
        <p>
            <b>Title:</b> <%= book.getName() %><br>
            <b>Author:</b> <%= book.getAuthor() %><br>
            <b>Price:</b> $<%= book.getPrice() %><br>
            <form action="AddToCartServlet" method="post">
                <input type="hidden" name="itemType" value="book">
                <input type="hidden" name="itemId" value="<%= book.getSerialNo() %>">
                <input type="submit" value="Add to Cart">
            </form>
        </p>
        <hr>
    <% } %>

    <h3>Available Accessories</h3>
    <%
        ArrayList<Accessory> accessories = AccessoryManager.getAllAccessories();
        for (Accessory accessory : accessories) {
    %>
        <p>
            <b>Name:</b> <%= accessory.getName() %><br>
            <b>Price:</b> $<%= accessory.getPrice() %><br>
            <form action="AddToCartServlet" method="post">
                <input type="hidden" name="itemType" value="accessory">
                <input type="hidden" name="itemId" value="<%= accessory.getSerialNo() %>">
                <input type="submit" value="Add to Cart">
            </form>
        </p>
        <hr>
    <% } %>

</body>
</html>
