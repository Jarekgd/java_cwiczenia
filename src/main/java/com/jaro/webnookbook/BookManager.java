package com.jaro.webnookbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * BookManager class for handling book operations
 */
public class BookManager {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    public static ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "SELECT b.serialNo, b.title, b.author, b.price, b.quantity, c.categoryId, c.categoryName " +
                             "FROM books b JOIN categories c ON b.categoryId = c.categoryId";
                try (PreparedStatement pstmt = connection.prepareStatement(sql);
                     ResultSet rs = pstmt.executeQuery()) {

                    while (rs.next()) {
                        Category category = new Category(rs.getInt("categoryId"), rs.getString("categoryName"));
                        books.add(new Book(
                            rs.getString("serialNo"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getDouble("price"),
                            rs.getInt("quantity"),
                            category
                        ));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public static Book getBookBySerialNo(String serialNo) {
        Book book = null;
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "SELECT b.serialNo, b.title, b.author, b.price, b.quantity, c.categoryId, c.categoryName " +
                             "FROM books b JOIN categories c ON b.categoryId = c.categoryId " +
                             "WHERE b.serialNo = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, serialNo);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            Category category = new Category(rs.getInt("categoryId"), rs.getString("categoryName"));
                            book = new Book(
                                rs.getString("serialNo"),
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getDouble("price"),
                                rs.getInt("quantity"),
                                category
                            );
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }
    public static void updateBookQuantity(String serialNo, int quantityPurchased) {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String checkSql = "SELECT quantity FROM books WHERE serialNo = ?";
                try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                    checkStmt.setString(1, serialNo);
                    ResultSet rs = checkStmt.executeQuery();
                    
                    if (rs.next()) {
                        int currentQuantity = rs.getInt("quantity");
                        int newQuantity = Math.max(0, currentQuantity - quantityPurchased); // Ensure quantity doesn't go negative

                        String updateSql = "UPDATE books SET quantity = ? WHERE serialNo = ?";
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
