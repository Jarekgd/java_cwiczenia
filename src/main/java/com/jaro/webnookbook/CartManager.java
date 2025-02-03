package com.jaro.webnookbook;

import java.sql.*;
import java.util.ArrayList;

public class CartManager {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    // Retrieve cart items for a user
    public static ArrayList<CartItem> getCart(String userLogin) {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "SELECT serialNo, name, price, quantity FROM cart WHERE userLogin = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, userLogin);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            cartItems.add(new CartItem(
                                rs.getString("serialNo"),
                                rs.getString("name"),
                                rs.getDouble("price"),
                                rs.getInt("quantity")
                            ));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    // Add item to cart
    public static void addToCart(String userLogin, String serialNo, String name, double price, int quantity) {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "INSERT INTO cart (userLogin, serialNo, name, price, quantity) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, userLogin);
                    pstmt.setString(2, serialNo);
                    pstmt.setString(3, name);
                    pstmt.setDouble(4, price);
                    pstmt.setInt(5, quantity);
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update cart item quantity
    public static void updateCart(String userLogin, String serialNo, int quantity) {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "UPDATE cart SET quantity = ? WHERE userLogin = ? AND serialNo = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setInt(1, quantity);
                    pstmt.setString(2, userLogin);
                    pstmt.setString(3, serialNo);
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Remove item from cart
    public static void removeFromCart(String userLogin, String serialNo) {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "DELETE FROM cart WHERE userLogin = ? AND serialNo = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, userLogin);
                    pstmt.setString(2, serialNo);
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Checkout: Reduce stock, remove from cart, create order, update balance
    public static boolean checkout(String userLogin, double totalAmount) {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                connection.setAutoCommit(false);

                // Check user balance
                String balanceQuery = "SELECT balance FROM users WHERE login = ?";
                try (PreparedStatement balanceStmt = connection.prepareStatement(balanceQuery)) {
                    balanceStmt.setString(1, userLogin);
                    ResultSet rs = balanceStmt.executeQuery();
                    if (rs.next() && rs.getDouble("balance") >= totalAmount) {
                        
                        // Deduct amount from balance
                        String updateBalance = "UPDATE users SET balance = balance - ? WHERE login = ?";
                        try (PreparedStatement updateStmt = connection.prepareStatement(updateBalance)) {
                            updateStmt.setDouble(1, totalAmount);
                            updateStmt.setString(2, userLogin);
                            updateStmt.executeUpdate();
                        }

                        // Clear cart
                        String deleteCart = "DELETE FROM cart WHERE userLogin = ?";
                        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteCart)) {
                            deleteStmt.setString(1, userLogin);
                            deleteStmt.executeUpdate();
                        }

                        connection.commit();
                        return true;
                    }
                }
                connection.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
