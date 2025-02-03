/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jaro.webnookbook;

/**
 *
 * @author jaros
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Cateogory {

    public static void createCategory() {
        String URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";
        String sql = "INSERT INTO categories(categoryName) VALUES ('Warr');";
        
        try {
            Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
