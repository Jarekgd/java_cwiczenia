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

@WebServlet("/DeleteUserServlet")  
public class DeleteUserServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));

        if (userId <= 0) {
            response.sendRedirect("manageUsers.jsp?error=Invalid user ID");
            return;
        }

        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "DELETE FROM users WHERE usrId = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setInt(1, userId);
                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        response.sendRedirect("manageUsers.jsp?success=User deleted successfully");
                    } else {
                        response.sendRedirect("manageUsers.jsp?error=User not found");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("manageUsers.jsp?error=Database error");
        }
    }
}
