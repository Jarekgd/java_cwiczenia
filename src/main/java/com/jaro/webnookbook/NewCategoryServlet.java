package com.jaro.webnookbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author jaros
 */
@WebServlet("/NewCategoryServlet")
public class NewCategoryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryName = request.getParameter("categoryName");
        String dbUrl = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(dbUrl)) {
                String sql = "INSERT INTO categories(categoryName) VALUES (?);";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, categoryName);
                    int result = pstmt.executeUpdate();

                    out.println("<html><body>");
                    if (result > 0) {
                        out.println("<h3>You have successfully inserted a new category.</h3>");
                        out.println("<a href='index.jsp\'>Back to main page</a>");
                    } else {
                        out.println("<h3>Error: Inserting new category failed.</h3>");
                    }
                    out.println("</body></html>");
                }
            }
        } catch (SQLException e) {
            response.getWriter().write("<html><body><h3>Error: Database operation failed.</h3></body></html>");
            System.err.println("Database operation failed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            response.getWriter().write("<html><body><h3>Error: SQLite driver not found.</h3></body></html>");
            System.err.println("SQLite driver not found.");
        }
    }
}
