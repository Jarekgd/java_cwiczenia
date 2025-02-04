<%@ page import="com.jaro.webnookbook.Order, java.util.ArrayList" %>
<%@ page session="true" %>

<%
    String userLogin = (String) session.getAttribute("userLogin");
    if (userLogin == null) {
        response.sendRedirect("login.jsp?error=Please log in to view orders");
        return;
    }

    ArrayList<Order> orders = (ArrayList<Order>) request.getAttribute("orders");
    if (orders == null) orders = new ArrayList<>();
%>

<h2>Order History</h2>

<% if (orders.isEmpty()) { %>
    <p>No orders found.</p>
<% } else { %>
    <table border="1">
        <tr>
            <th>Order ID</th>
            <th>Total Amount</th>
            <th>Order Date</th>
            <th>Status</th>
        </tr>
        <% for (Order order : orders) { %>
        <tr>
            <td><%= order.getOrderId() %></td>
            <td>$<%= order.getTotalAmount() %></td>
            <td><%= order.getOrderDate() %></td>
            <td><%= order.getStatus() %></td>
        </tr>
        <% } %>
    </table>
<% } %>
