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

@WebServlet("/AddCategoryServlet")
public class AddCategoryServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryName = request.getParameter("categoryName");

        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "INSERT INTO categories (categoryName) VALUES (?)";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, categoryName);
                    pstmt.executeUpdate();
                    response.sendRedirect("manageCategories.jsp");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("manageCategories.jsp?error=1");
        }
    }
}
