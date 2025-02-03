package com.jaro.webnookbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * AccessoryManager class for handling accessory retrieval
 */
public class AccessoryManager {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    public static ArrayList<Accessory> getAllAccessories() {
        ArrayList<Accessory> accessories = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "SELECT * FROM accessories";
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
}
