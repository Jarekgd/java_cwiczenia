package com.jaro.webnookbook;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/RemoveFromCartServlet")
public class RemoveFromCartServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userLogin = (String) session.getAttribute("userLogin");

        if (userLogin == null) {
            response.sendRedirect("login.jsp?error=Please log in to modify your cart");
            return;
        }

        String serialNo = request.getParameter("serialNo");

        if (serialNo == null || serialNo.isEmpty()) {
            response.sendRedirect("customerCart.jsp?error=Invalid item");
            return;
        }

        try {
            // Remove item from cart
            CartManager.removeFromCart(userLogin, serialNo);
            response.sendRedirect("customerCart.jsp?success=Item removed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("customerCart.jsp?error=Database error");
        }
    }
}
