<%@ page session="true" %>
<%
    if (!"Customer".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
    }

    String userLogin = (String) session.getAttribute("userLogin");
%>

<html>
<head>
    <title>Customer Dashboard</title>
</head>
<body>
    <h2>Welcome, <%= userLogin %>!</h2>

    <h3>Shopping Options</h3>
    <ul>
        <li><a href="customerViewBooks.jsp">View Books</a></li>
        <li><a href="customerViewAccessories.jsp">View Accessories</a></li>
    </ul>

    <h3>Your Shopping Cart</h3>
    <ul>
        <li><a href="customerCart.jsp">View & Edit Cart</a></li>
        <li><a href="CheckoutServlet">Proceed to Checkout</a></li>
    </ul>

    <h3>Account Management</h3>
    <ul>
        <li><a href="editAccount.jsp">Edit Account Details</a></li>
        <li><a href="logout.jsp">Logout</a></li>
    </ul>
</body>
</html>
