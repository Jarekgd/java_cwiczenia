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

@WebServlet("/AddAccessoryServlet")
public class AddAccessoryServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String serialNo = request.getParameter("serialNo");
        String name = request.getParameter("accessoryName");
        double price = Double.parseDouble(request.getParameter("accessoryPrice"));
        int quantity = Integer.parseInt(request.getParameter("accessoryQuantity"));

        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "INSERT INTO accessories (serialNo, accessoryName, price, quantity) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, serialNo);
                    pstmt.setString(2, name);
                    pstmt.setDouble(3, price);
                    pstmt.setInt(4, quantity);
                    pstmt.executeUpdate();
                    response.sendRedirect("manageAccessories.jsp?success=Accessory added successfully");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("addAccessory.jsp?error=Database error");
        }
    }
}
