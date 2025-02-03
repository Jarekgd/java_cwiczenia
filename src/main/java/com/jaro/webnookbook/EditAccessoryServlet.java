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

@WebServlet("/EditAccessoryServlet")  
public class EditAccessoryServlet extends HttpServlet {
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
                String sql = "UPDATE accessories SET accessoryName = ?, price = ?, quantity = ? WHERE serialNo = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, name);
                    pstmt.setDouble(2, price);
                    pstmt.setInt(3, quantity);
                    pstmt.setString(4, serialNo);
                    pstmt.executeUpdate();
                    response.sendRedirect("manageAccessories.jsp?success=Accessory updated");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("editAccessory.jsp?id=" + serialNo + "&error=Database error");
        }
    }
}
