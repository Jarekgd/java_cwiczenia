<%@ page import="com.jaro.webnookbook.OrderManager" %>
<%@ page import="com.jaro.webnookbook.Order" %>
<%@ page import="com.jaro.webnookbook.OrderItem" %>
<%@ page import="java.util.ArrayList" %>
<%@ page session="true" %>

<%
    if (!"Admin".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }

    ArrayList<Order> orders = OrderManager.getAllOrders();
%>

<html>
<head>
    <title>All Orders</title>
</head>
<body>
    <h2>All Users' Orders</h2>

    <table border="1">
        <tr>
            <th>Order ID</th>
            <th>User</th>
            <th>Total Amount</th>
            <th>Order Date</th>
            <th>Items</th>
        </tr>
        <% for (Order order : orders) { %>
            <tr>
                <td><%= order.getOrderId() %></td>
                <td><%= order.getUserLogin() %></td>
                <td>$<%= order.getTotalAmount() %></td>
                <td><%= order.getOrderDate() %></td>
                <td>
                    <% ArrayList<OrderItem> items = OrderManager.getOrderItems(order.getOrderId()); %>
                    <ul>
                        <% for (OrderItem item : items) { %>
                            <li><%= item.getName() %> (x<%= item.getQuantity() %>) - $<%= item.getPrice() %></li>
                        <% } %>
                    </ul>
                </td>
            </tr>
        <% } %>
    </table>

    <a href="adminDashboard.jsp">Back to Dashboard</a>
</body>
</html>
