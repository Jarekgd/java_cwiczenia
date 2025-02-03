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
import jakarta.servlet.http.HttpSession;

@WebServlet("/EditAccountServlet")
public class EditAccountServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String oldLogin = (String) session.getAttribute("userLogin");

        if (oldLogin == null) {
            response.sendRedirect("login.jsp");  // Redirect if not logged in
            return;
        }

        String newEmail = request.getParameter("email");
        String newUserName = request.getParameter("userName");
        String newLogin = request.getParameter("login");
        String newPassword = request.getParameter("password");

        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "UPDATE users SET email = ?, userName = ?, login = ?, password = ? WHERE login = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, newEmail);
                    pstmt.setString(2, newUserName);
                    pstmt.setString(3, newLogin);
                    pstmt.setString(4, newPassword);
                    pstmt.setString(5, oldLogin);

                    int rowsUpdated = pstmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        session.setAttribute("userLogin", newLogin);
                        session.setAttribute("userName", newUserName);
                        session.setAttribute("userEmail", newEmail);

                        response.sendRedirect("editAccount.jsp?success=1");
                    } else {
                        response.sendRedirect("editAccount.jsp?error=1");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("editAccount.jsp?error=1");
        }
    }
}
