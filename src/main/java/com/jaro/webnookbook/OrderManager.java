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
}
