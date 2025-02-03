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

@WebServlet("/DeleteCategoryServlet")
public class DeleteCategoryServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int categoryId;

        try {
            categoryId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendRedirect("manageCategories.jsp?error=Invalid category ID");
            return;
        }

        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "DELETE FROM categories WHERE categoryId = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setInt(1, categoryId);
                    pstmt.executeUpdate();
                    response.sendRedirect("manageCategories.jsp?success=Category deleted successfully");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("manageCategories.jsp?error=Database error");
        }
    }
}
