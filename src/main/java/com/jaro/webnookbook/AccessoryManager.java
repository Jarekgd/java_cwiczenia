package com.jaro.webnookbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * AccessoryManager class for handling accessory operations
 */
public class AccessoryManager {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";


    public static ArrayList<Accessory> getAllAccessories() {
        ArrayList<Accessory> accessories = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "SELECT serialNo, accessoryName, price, quantity FROM accessories"; 
                try (PreparedStatement pstmt = connection.prepareStatement(sql);
                     ResultSet rs = pstmt.executeQuery()) {

                    while (rs.next()) {
                        accessories.add(new Accessory(
                            rs.getString("serialNo"),
                            rs.getString("accessoryName"),
                            rs.getDouble("price"),
                            rs.getInt("quantity")
                        ));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessories;
    }

 
    public static Accessory getAccessoryBySerialNo(String serialNo) {
        Accessory accessory = null;
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "SELECT serialNo, accessoryName, price, quantity FROM accessories WHERE serialNo = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, serialNo);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            accessory = new Accessory(
                                rs.getString("serialNo"),
                                rs.getString("accessoryName"),
                                rs.getDouble("price"),
                                rs.getInt("quantity")
                            );
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessory;
    }
    public static void updateAccessoryQuantity(String serialNo, int quantityPurchased) {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String checkSql = "SELECT quantity FROM accessories WHERE serialNo = ?";
                try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                    checkStmt.setString(1, serialNo);
                    ResultSet rs = checkStmt.executeQuery();
                    
                    if (rs.next()) {
                        int currentQuantity = rs.getInt("quantity");
                        int newQuantity = Math.max(0, currentQuantity - quantityPurchased); // Ensure quantity doesn't go negative

                        String updateSql = "UPDATE accessories SET quantity = ? WHERE serialNo = ?";
                        try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                            updateStmt.setInt(1, newQuantity);
                            updateStmt.setString(2, serialNo);
                            updateStmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
