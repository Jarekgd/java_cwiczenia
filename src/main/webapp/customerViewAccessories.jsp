<%@ page import="com.jaro.webnookbook.AccessoryManager" %>
<%@ page import="com.jaro.webnookbook.Accessory" %>
<%@ page import="java.util.ArrayList" %>
<%@ page session="true" %>
<%
    if (!"Customer".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }

    ArrayList<Accessory> accessories = AccessoryManager.getAllAccessories();
%>

<html>
<head>
    <title>Available Accessories</title>
</head>
<body>
    <h2>Accessories</h2>

    <table border="1">
        <tr>
            <th>Serial Number</th>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Action</th>
        </tr>
        <% for (Accessory accessory : accessories) { %>
            <tr>
                <td><%= accessory.getSerialNo() %></td>
                <td><%= accessory.getName() %></td>
                <td>$<%= accessory.getPrice() %></td>
                <td><%= accessory.getQuantity() %></td>
                <td>
                    <% if (accessory.getQuantity() > 0) { %>
                        <form action="AddToCartServlet" method="post">
                            <input type="hidden" name="productType" value="accessory">
                            <input type="hidden" name="serialNo" value="<%= accessory.getSerialNo() %>">
                            <input type="number" name="quantity" value="1" min="1">
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
