package com.jaro.webnookbook;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UpdateOrderServlet")
public class UpdateOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("userRole");

        if (userRole == null) {
            response.sendRedirect("login.jsp?error=Unauthorized access");
            return;
        }

        String orderIdParam = request.getParameter("orderId");
        String newStatus = request.getParameter("status");

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("manageOrders.jsp?error=Invalid Order ID");
            return;
        }

        boolean success = OrderManager.updateOrderStatus(orderId, newStatus);

        if (success) {
            response.sendRedirect("manageOrders.jsp?success=Order updated successfully");
        } else {
            response.sendRedirect("manageOrders.jsp?error=Failed to update order");
        }
    }
}
