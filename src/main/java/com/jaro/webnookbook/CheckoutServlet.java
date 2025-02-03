package com.jaro.webnookbook;

import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userLogin = (String) session.getAttribute("userLogin");

        if (userLogin == null) {
            response.sendRedirect("login.jsp?error=Please log in to proceed with checkout");
            return;
        }

        ArrayList<CartItem> cartItems = CartManager.getCart(userLogin);

        if (cartItems.isEmpty()) {
            response.sendRedirect("customerCart.jsp?error=Your cart is empty");
            return;
        }

        double totalAmount = 0.0;
        for (CartItem item : cartItems) {
            totalAmount += item.getPrice() * item.getQuantity();
        }

        int orderId = OrderManager.createOrder(userLogin, totalAmount, cartItems);

        if (orderId > 0) {
            CartManager.clearCart(userLogin);
            response.sendRedirect("customerOrders.jsp?success=Order placed successfully");
        } else {
            response.sendRedirect("customerCart.jsp?error=Failed to place order");
        }
    }
}
