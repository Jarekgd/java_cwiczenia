<%@ page session="true" %>
<%
    if (!"Admin".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }
%>

<html>
<head>
    <title>Add Accessory</title>
</head>
<body>
    <h2>Add New Accessory</h2>
    
    <form action="AddAccessoryServlet" method="post">
        <label>Serial Number:</label>
        <input type="text" name="serialNo" required><br>

        <label>Accessory Name:</label>
        <input type="text" name="accessoryName" required><br>

        <label>Price:</label>
        <input type="number" step="0.01" name="accessoryPrice" required><br>

        <label>Quantity:</label>
        <input type="number" name="accessoryQuantity" required><br>

        <button type="submit">Add Accessory</button>
    </form>

    <a href="manageAccessories.jsp">Back to Accessories</a>
</body>
</html>
