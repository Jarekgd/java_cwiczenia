package com.jaro.webnookbook;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddBookServlet")
public class AddBookServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String serialNo = request.getParameter("serialNo");
        String name = request.getParameter("bookName");
        String author = request.getParameter("author");
        double price = Double.parseDouble(request.getParameter("bookPrice"));
        int quantity = Integer.parseInt(request.getParameter("bookQuantity"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "INSERT INTO books (serialNo, title, author, price, quantity, categoryId) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, serialNo);
                    pstmt.setString(2, name);
                    pstmt.setString(3, author);
                    pstmt.setDouble(4, price);
                    pstmt.setInt(5, quantity);
                    pstmt.setInt(6, categoryId);
                    pstmt.executeUpdate();
                    response.sendRedirect("manageBooks.jsp?success=Book added successfully");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addBook.jsp?error=Database error");
        }
    }
}
