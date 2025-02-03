<%@ page session="true" %>

<%
    // Redirect if not an Admin
    if (!"Admin".equals(session.getAttribute("userRole"))) {     
        response.sendRedirect("login.jsp"); 
    }
%>

<html>
<head>
    <title>Admin Dashboard</title>
</head>
<body>
    <h2>Welcome, Admin <%= session.getAttribute("userLogin") %></h2>

    <h3>Manage Store</h3>
    <ul>
    <li><a href="manageBooks.jsp">Manage Books</a></li>
    <li><a href="manageAccessories.jsp">Manage Accessories</a></li>
    <li><a href="manageCategories.jsp">Manage Categories</a></li>
    <li><a href="manageUsers.jsp">Manage Users</a></li>
    
</ul>


    <a href="logout.jsp">Logout</a>
</body>
</html>

