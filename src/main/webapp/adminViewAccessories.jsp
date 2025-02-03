<%@ page import="com.jaro.webnookbook.AccessoryManager" %>
<%@ page import="com.jaro.webnookbook.Accessory" %>
<%@ page import="java.util.ArrayList" %>
<%@ page session="true" %>
<%
    if (!"Admin".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }

    ArrayList<Accessory> accessories = AccessoryManager.getAllAccessories();
%>

<html>
<head>
    <title>Admin - View Accessories</title>
</head>
<body>
    <h2>Accessories</h2>

    <table border="1">
        <tr>
            <th>Serial Number</th>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Actions</th>
        </tr>
        <% for (Accessory accessory : accessories) { %>
            <tr>
                <td><%= accessory.getSerialNo() %></td>
                <td><%= accessory.getName() %></td>
                <td><%= accessory.getPrice() %></td>
                <td><%= accessory.getQuantity() %></td>
                <td>
                    <a href="editAccessory.jsp?id=<%= accessory.getSerialNo() %>">Edit</a> |
                    <a href="DeleteAccessoryServlet?id=<%= accessory.getSerialNo() %>" onclick="return confirm('Are you sure?');">Delete</a>
                </td>
            </tr>
        <% } %>
    </table>

    <a href="adminDashboard.jsp">Back to Dashboard</a>
</body>
</html>
