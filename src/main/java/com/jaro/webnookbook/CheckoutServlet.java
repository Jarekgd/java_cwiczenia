package com.jaro.webnookbook;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userLogin = (String) session.getAttribute("userLogin");

        if (userLogin == null) {
            response.sendRedirect("login.jsp?error=Please log in to checkout");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            connection.setAutoCommit(false); // ✅ Begin transaction

            // Retrieve cart items
            ArrayList<CartItem> cartItems = CartManager.getCart(userLogin);
            if (cartItems.isEmpty()) {
                response.sendRedirect("customerCart.jsp?error=Your cart is empty");
                return;
            }

            // Calculate total amount
            double totalAmount = 0.0;
            for (CartItem item : cartItems) {
                totalAmount += item.getPrice() * item.getQuantity();
            }

            // Check user balance
            String balanceQuery = "SELECT balance FROM users WHERE login = ?";
            try (PreparedStatement balanceStmt = connection.prepareStatement(balanceQuery)) {
                balanceStmt.setString(1, userLogin);
                ResultSet rs = balanceStmt.executeQuery();
                if (rs.next() && rs.getDouble("balance") < totalAmount) {
                    response.sendRedirect("customerCart.jsp?error=Insufficient balance");
                    return;
                }
            }

            // Deduct the amount from user balance
            String updateBalanceQuery = "UPDATE users SET balance = balance - ? WHERE login = ?";
            try (PreparedStatement updateBalanceStmt = connection.prepareStatement(updateBalanceQuery)) {
                updateBalanceStmt.setDouble(1, totalAmount);
                updateBalanceStmt.setString(2, userLogin);
                updateBalanceStmt.executeUpdate();
            }

            // Create an order record
            String orderQuery = "INSERT INTO orders (userLogin, totalAmount) VALUES (?, ?)";
            try (PreparedStatement orderStmt = connection.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setString(1, userLogin);
                orderStmt.setDouble(2, totalAmount);
                orderStmt.executeUpdate();
                
                ResultSet generatedKeys = orderStmt.getGeneratedKeys();
                int orderId = -1;
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1);
                }

                // Deduct stock quantity and insert order items
                for (CartItem item : cartItems) {
                    // Reduce stock for books/accessories
                    String updateStockQuery = "UPDATE books SET quantity = quantity - ? WHERE serialNo = ? " +
                                              "UNION " +
                                              "UPDATE accessories SET quantity = quantity - ? WHERE serialNo = ?";
                    try (PreparedStatement stockStmt = connection.prepareStatement(updateStockQuery)) {
                        stockStmt.setInt(1, item.getQuantity());
                        stockStmt.setString(2, item.getSerialNo());
                        stockStmt.setInt(3, item.getQuantity());
                        stockStmt.setString(4, item.getSerialNo());
                        stockStmt.executeUpdate();
                    }

                    // Insert into order items
                    String orderItemsQuery = "INSERT INTO order_items (orderId, serialNo, name, price, quantity) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement orderItemsStmt = connection.prepareStatement(orderItemsQuery)) {
                        orderItemsStmt.setInt(1, orderId);
                        orderItemsStmt.setString(2, item.getSerialNo());
                        orderItemsStmt.setString(3, item.getName());
                        orderItemsStmt.setDouble(4, item.getPrice());
                        orderItemsStmt.setInt(5, item.getQuantity());
                        orderItemsStmt.executeUpdate();
                    }
                }

                // Clear cart after successful purchase
                String clearCartQuery = "DELETE FROM cart WHERE userLogin = ?";
                try (PreparedStatement clearCartStmt = connection.prepareStatement(clearCartQuery)) {
                    clearCartStmt.setString(1, userLogin);
                    clearCartStmt.executeUpdate();
                }

                connection.commit(); // ✅ Commit transaction
                response.sendRedirect("customerDashboard.jsp?success=Order placed successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("customerCart.jsp?error=Checkout failed");
        }
    }
}
