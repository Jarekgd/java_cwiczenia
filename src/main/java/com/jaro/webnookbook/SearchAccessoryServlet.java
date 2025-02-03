/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.jaro.webnookbook;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author jaros
 */
@WebServlet("/SearchAccessoryServlet")
public class SearchAccessoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");
        String dbUrl = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(dbUrl)) {
                String sql = "SELECT * FROM accessories WHERE accessoryName LIKE ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, "%" + query + "%");
                    try (ResultSet rs = pstmt.executeQuery()) {
                        out.println("<html><body><h2>Search Results</h2>");
                        while (rs.next()) {
                            out.println("<p>" + rs.getString("accessoryName") + " - $" + rs.getString("price") + "</p>");
                        }
                        out.println("<a href='customerDashboard.jsp'>Back</a></body></html>");
                    }
                }
            }
        } catch (Exception e) {
            response.getWriter().write("<html><body><h3>Error: Database operation failed.</h3></body></html>");
            e.printStackTrace();
        }
    }
}
