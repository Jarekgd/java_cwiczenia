<%@ page import="com.jaro.webnookbook.OrderManager" %>
<%@ page import="com.jaro.webnookbook.Order" %>
<%@ page import="java.util.ArrayList" %>
<%@ page session="true" %>

<%
    if (session.getAttribute("userRole") == null || !"Admin".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
        return;
    }

    ArrayList<Order> orders = OrderManager.getAllOrders();
%>

<html>
<head>
    <title>All Users Orders</title>
</head>
<body>
    <h2>All Users' Orders</h2>

    <table border="1">
        <tr>
            <th>Order ID</th>
            <th>User</th>
            <th>Total Amount</th>
            <th>Order Date</th>
            <th>Status</th>
            <th>Details</th>
        </tr>

        <% for (Order order : orders) { %>
        <tr>
            <td><%= order.getOrderId() %></td>
            <td><%= order.getUserLogin() %></td>
            <td>$<%= order.getTotalAmount() %></td>
            <td><%= order.getOrderDate() %></td>
            <td><%= order.getStatus() %></td>
            <td><a href="viewOrderDetails.jsp?orderId=<%= order.getOrderId() %>">View</a></td>
        </tr>
        <% } %>

    </table>

    <a href="adminDashboard.jsp">Back to Admin Dashboard</a>
</body>
</html>
