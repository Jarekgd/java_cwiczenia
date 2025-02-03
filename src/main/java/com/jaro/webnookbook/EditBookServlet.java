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

@WebServlet("/EditBookServlet")
public class EditBookServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String serialNo = request.getParameter("serialNo");
        String title = request.getParameter("title"); 
        String author = request.getParameter("author");
        double price = Double.parseDouble(request.getParameter("bookPrice"));
        int quantity = Integer.parseInt(request.getParameter("bookQuantity"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "UPDATE books SET title = ?, author = ?, price = ?, quantity = ?, categoryId = ? WHERE serialNo = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, title); 
                    pstmt.setString(2, author);
                    pstmt.setDouble(3, price);
                    pstmt.setInt(4, quantity);
                    pstmt.setInt(5, categoryId);
                    pstmt.setString(6, serialNo);
                    pstmt.executeUpdate();
                    response.sendRedirect("manageBooks.jsp?success=Book updated");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("editBook.jsp?id=" + serialNo + "&error=Database error");
        }
    }
}
