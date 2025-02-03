package com.jaro.webnookbook;

import java.sql.*;
import java.util.ArrayList;

public class OrderManager {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    public static ArrayList<Order> getUserOrders(String userLogin) {
        ArrayList<Order> orders = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "SELECT orderId, totalAmount, orderDate FROM orders WHERE userLogin = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, userLogin);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            orders.add(new Order(
                                rs.getInt("orderId"),
                                userLogin,
                                rs.getDouble("totalAmount"),
                                rs.getString("orderDate")
                            ));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static ArrayList<Order> getAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "SELECT orderId, userLogin, totalAmount, orderDate FROM orders";
                try (PreparedStatement pstmt = connection.prepareStatement(sql);
                     ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        orders.add(new Order(
                            rs.getInt("orderId"),
                            rs.getString("userLogin"),
                            rs.getDouble("totalAmount"),
                            rs.getString("orderDate")
                        ));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
    public static int createOrder(String userLogin, double totalAmount, ArrayList<CartItem> cartItems) {
        int orderId = -1;
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                // Insert new order
                String insertOrderSQL = "INSERT INTO orders (userLogin, totalAmount) VALUES (?, ?)";
                try (PreparedStatement orderStmt = connection.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS)) {
                    orderStmt.setString(1, userLogin);
                    orderStmt.setDouble(2, totalAmount);
                    orderStmt.executeUpdate();

                    // Get generated order ID
                    ResultSet rs = orderStmt.getGeneratedKeys();
                    if (rs.next()) {
                        orderId = rs.getInt(1);
                    }
                }

                // Insert each item in order_items
                if (orderId > 0) {
                    String insertItemSQL = "INSERT INTO order_items (orderId, serialNo, name, price, quantity) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement itemStmt = connection.prepareStatement(insertItemSQL)) {
                        for (CartItem item : cartItems) {
                            itemStmt.setInt(1, orderId);
                            itemStmt.setString(2, item.getSerialNo());
                            itemStmt.setString(3, item.getName());
                            itemStmt.setDouble(4, item.getPrice());
                            itemStmt.setInt(5, item.getQuantity());
                            itemStmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderId;
    }
    public static boolean updateOrderStatus(int orderId, String status) {
        boolean updated = false;
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "UPDATE orders SET status = ? WHERE orderId = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, status);
                    pstmt.setInt(2, orderId);
                    int rowsAffected = pstmt.executeUpdate();
                    updated = (rowsAffected > 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
    }

    public static Order getOrderById(int orderId) {
        Order order = null;
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "SELECT orderId, userLogin, totalAmount, orderDate, status FROM orders WHERE orderId = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setInt(1, orderId);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            order = new Order(
                                rs.getInt("orderId"),
                                rs.getString("userLogin"),
                                rs.getDouble("totalAmount"),
                                rs.getString("orderDate"),
                                rs.getString("status") 
                            );
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }
    public static ArrayList<OrderItem> getOrderItems(int orderId) {
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "SELECT serialNo, name, price, quantity FROM order_items WHERE orderId = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setInt(1, orderId);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            orderItems.add(new OrderItem(
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
        return orderItems;
    }
}



