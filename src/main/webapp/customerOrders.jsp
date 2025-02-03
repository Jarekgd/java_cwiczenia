<%@ page import="com.jaro.webnookbook.OrderManager" %>
<%@ page import="com.jaro.webnookbook.Order" %>
<%@ page import="java.util.ArrayList" %>
<%@ page session="true" %>

<%
    if (session.getAttribute("userLogin") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String userLogin = (String) session.getAttribute("userLogin");
    ArrayList<Order> orders = OrderManager.getUserOrders(userLogin);
%>

<html>
<head>
    <title>My Orders</title>
</head>
<body>
    <h2>My Order History</h2>

    <table border="1">
        <tr>
            <th>Order ID</th>
            <th>Total Amount</th>
            <th>Order Date</th>
            <th>Status</th>
            <th>Details</th>
        </tr>

        <% for (Order order : orders) { %>
        <tr>
            <td><%= order.getOrderId() %></td>
            <td>$<%= order.getTotalAmount() %></td>
            <td><%= order.getOrderDate() %></td>
            <td><%= order.getStatus() %></td>
            <td><a href="viewOrderDetails.jsp?orderId=<%= order.getOrderId() %>">View</a></td>
        </tr>
        <% } %>

    </table>

    <a href="customerDashboard.jsp">Back to Dashboard</a>
</body>
</html>
