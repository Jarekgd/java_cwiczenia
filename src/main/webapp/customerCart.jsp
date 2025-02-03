<%@ page import="com.jaro.webnookbook.CartManager" %>
<%@ page import="com.jaro.webnookbook.CartItem" %>
<%@ page import="java.util.ArrayList" %>
<%@ page session="true" %>

<%
    if (!"Customer".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }

    String userLogin = (String) session.getAttribute("userLogin");
    ArrayList<CartItem> cartItems = CartManager.getCart(userLogin);
    double totalAmount = 0.0;
%>

<html>
<head>
    <title>Your Shopping Cart</title>
</head>
<body>
    <h2>Your Shopping Cart</h2>

    <table border="1">
        <tr>
            <th>Item</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Subtotal</th>
            <th>Actions</th>
        </tr>
        <% for (CartItem item : cartItems) { 
            double subtotal = item.getPrice() * item.getQuantity();
            totalAmount += subtotal;
        %>
            <tr>
                <td><%= item.getName() %></td>
                <td>$<%= item.getPrice() %></td>
                <td>
                    <form action="UpdateCartServlet" method="post">
                        <input type="hidden" name="serialNo" value="<%= item.getSerialNo() %>">
                        <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1">
                        <button type="submit">Update</button>
                    </form>
                </td>
                <td>$<%= subtotal %></td>
                <td>
                    <a href="RemoveFromCartServlet?serialNo=<%= item.getSerialNo() %>">Remove</a>
                </td>
            </tr>
        <% } %>
    </table>

    <h3>Total: $<%= totalAmount %></h3>

    <% if (!cartItems.isEmpty()) { %>
        <form action="CheckoutServlet" method="post">
            <button type="submit">Checkout</button>
        </form>
    <% } else { %>
        <p>Your cart is empty.</p>
    <% } %>

    <a href="customerDashboard.jsp">Continue Shopping</a>
</body>
</html>
