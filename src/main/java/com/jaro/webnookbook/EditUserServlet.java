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

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String email = request.getParameter("email");
        String userName = request.getParameter("userName");
        String login = request.getParameter("login");
        String role = request.getParameter("role");

        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "UPDATE users SET email = ?, userName = ?, login = ?, privilege = (SELECT roleId FROM roles WHERE roleName = ?) WHERE usrId = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, email);
                    pstmt.setString(2, userName);
                    pstmt.setString(3, login);
                    pstmt.setString(4, role);
                    pstmt.setInt(5, userId);
                    pstmt.executeUpdate();
                    response.sendRedirect("manageUsers.jsp?success=User updated");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("editUser.jsp?id=" + userId + "&error=Database error");
        }
    }
}
