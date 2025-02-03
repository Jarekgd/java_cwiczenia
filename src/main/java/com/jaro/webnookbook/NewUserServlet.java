package com.solent.booknookm2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/NewUserServlet")
public class NewUserServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String userName = request.getParameter("userName");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "INSERT INTO users (email, userName, login, password, role) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, email);
                    pstmt.setString(2, userName);
                    pstmt.setString(3, login);
                    pstmt.setString(4, password);
                    pstmt.setString(5, role);

                    pstmt.executeUpdate();
                    response.sendRedirect("login.jsp?message=Registration successful");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=Error registering user");
        }
    }
}
