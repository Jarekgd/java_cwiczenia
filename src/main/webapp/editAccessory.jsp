<%@ page import="com.jaro.webnookbook.AccessoryManager" %>
<%@ page import="com.jaro.webnookbook.Accessory" %>
<%@ page session="true" %>
<%
    if (!"Admin".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }

    String serialNo = request.getParameter("id");
    Accessory accessory = AccessoryManager.getAccessoryBySerialNo(serialNo);

    if (accessory == null) {
        response.sendRedirect("manageAccessories.jsp?error=Accessory not found");
        return;
    }
%>

<html>
<head>
    <title>Edit Accessory</title>
</head>
<body>
    <h2>Edit Accessory</h2>

    <form action="EditAccessoryServlet" method="post">
        <input type="hidden" name="serialNo" value="<%= accessory.getSerialNo() %>">

        <label>Accessory Name:</label>
        <input type="text" name="accessoryName" value="<%= accessory.getName() %>" required><br>

        <label>Price:</label>
        <input type="number" step="0.01" name="accessoryPrice" value="<%= accessory.getPrice() %>" required><br>

        <label>Quantity:</label>
        <input type="number" name="accessoryQuantity" value="<%= accessory.getQuantity() %>" required><br>

        <button type="submit">Update Accessory</button>
    </form>

    <a href="manageAccessories.jsp">Back</a>
</body>
</html>
