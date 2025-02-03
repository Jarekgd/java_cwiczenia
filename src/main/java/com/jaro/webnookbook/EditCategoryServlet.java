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

@WebServlet("/EditCategoryServlet")
public class EditCategoryServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int categoryId;
        String categoryName = request.getParameter("categoryName");

        try {
            categoryId = Integer.parseInt(request.getParameter("categoryId"));
        } catch (NumberFormatException e) {
            response.sendRedirect("manageCategories.jsp?error=Invalid category ID");
            return;
        }

        if (categoryName == null || categoryName.trim().isEmpty()) {
            response.sendRedirect("editCategory.jsp?id=" + categoryId + "&error=Category name cannot be empty");
            return;
        }

        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "UPDATE categories SET categoryName = ? WHERE categoryId = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, categoryName);
                    pstmt.setInt(2, categoryId);
                    pstmt.executeUpdate();
                    response.sendRedirect("manageCategories.jsp?success=Category updated successfully");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("editCategory.jsp?id=" + categoryId + "&error=Database error");
        }
    }
}
