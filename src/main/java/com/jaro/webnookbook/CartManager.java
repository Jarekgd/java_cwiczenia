package com.jaro.webnookbook;

import java.sql.*;
import java.util.ArrayList;

public class CartManager {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

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


    public static void addToCart(String userLogin, String serialNo, String name, double price, int quantity) {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                // Check if item already exists in the cart
                String checkSql = "SELECT quantity FROM cart WHERE userLogin = ? AND serialNo = ?";
                try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                    checkStmt.setString(1, userLogin);
                    checkStmt.setString(2, serialNo);
                    ResultSet rs = checkStmt.executeQuery();

                    if (rs.next()) {
                        // Item exists, update quantity
                        int newQuantity = rs.getInt("quantity") + quantity;
                        String updateSql = "UPDATE cart SET quantity = ? WHERE userLogin = ? AND serialNo = ?";
                        try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                            updateStmt.setInt(1, newQuantity);
                            updateStmt.setString(2, userLogin);
                            updateStmt.setString(3, serialNo);
                            updateStmt.executeUpdate();
                        }
                    } else {
                        // Item does not exist, insert new entry
                        String insertSql = "INSERT INTO cart (userLogin, serialNo, name, price, quantity) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                            insertStmt.setString(1, userLogin);
                            insertStmt.setString(2, serialNo);
                            insertStmt.setString(3, name);
                            insertStmt.setDouble(4, price);
                            insertStmt.setInt(5, quantity);
                            insertStmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
}
