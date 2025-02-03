package com.jaro.webnookbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * BookManager class for handling book retrieval
 */
public class BookManager {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    public static ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "SELECT * FROM books";
                try (PreparedStatement pstmt = connection.prepareStatement(sql);
                     ResultSet rs = pstmt.executeQuery()) {

                    while (rs.next()) {
                        books.add(new Book(
                            rs.getString("serialNo"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getDouble("price"),
                            rs.getInt("quantity"),
                            null  // No category retrieval in this example
                        ));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
