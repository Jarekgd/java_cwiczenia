<%@ page import="java.util.ArrayList" %>
<%@ page import="com.jaro.webnookbook.Accessory" %>
<%@ page import="com.jaro.webnookbook.AccessoryManager" %>

<%@ page session="true" %>
<%
    if (!"Admin".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }
%>

<html>
<head>
    <title>Manage Accessories</title>
</head>
<body>
    <h2>Manage Accessories</h2>
    <a href="addAccessory.jsp">Add New Accessory</a>

    <h3>Existing Accessories</h3>
    <%
        ArrayList<Accessory> accessories = AccessoryManager.getAllAccessories();
        for (Accessory accessory : accessories) {
    %>
        <p>
            <b>Name:</b> <%= accessory.getName() %><br>
            <b>Price:</b> $<%= accessory.getPrice() %><br>
            <a href="editAccessory.jsp?id=<%= accessory.getSerialNo() %>">Edit</a> | 
            <a href="DeleteAccessoryServlet?id=<%= accessory.getSerialNo() %>" onclick="return confirm('Are you sure?');">Delete</a>
        </p>
        <hr>
    <% } %>

    <a href="adminDashboard.jsp">Back</a>
</body>
</html>
