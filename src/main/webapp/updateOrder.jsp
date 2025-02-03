<%@ page import="com.jaro.webnookbook.OrderManager" %>
<%@ page import="com.jaro.webnookbook.Order" %>
<%@ page session="true" %>

<%
    if (session.getAttribute("userRole") == null) {
        response.sendRedirect("login.jsp");
    }

    String orderIdParam = request.getParameter("orderId");
    int orderId = Integer.parseInt(orderIdParam);
    Order order = OrderManager.getOrderById(orderId);

    if (order == null) {
        response.sendRedirect("manageOrders.jsp?error=Order not found");
    }
%>

<html>
<head>
    <title>Update Order</title>
</head>
<body>
    <h2>Update Order #<%= order.getOrderId() %></h2>

    <p><strong>User:</strong> <%= order.getUserLogin() %></p>
    <p><strong>Total Amount:</strong> $<%= order.getTotalAmount() %></p>
    <p><strong>Date:</strong> <%= order.getOrderDate() %></p>

    <form action="UpdateOrderServlet" method="post">
        <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
        <label>Order Status:</label>
        <select name="status">
            <option value="Pending" <% if ("Pending".equals(order.getStatus())) { %> selected <% } %>>Pending</option>
            <option value="Shipped" <% if ("Shipped".equals(order.getStatus())) { %> selected <% } %>>Shipped</option>
            <option value="Delivered" <% if ("Delivered".equals(order.getStatus())) { %> selected <% } %>>Delivered</option>
            <option value="Canceled" <% if ("Canceled".equals(order.getStatus())) { %> selected <% } %>>Canceled</option>
        </select><br>

        <button type="submit">Update Order</button>
    </form>

    <a href="manageOrders.jsp">Back to Orders</a>
</body>
</html>
