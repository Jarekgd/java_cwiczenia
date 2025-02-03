package com.jaro.webnookbook;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userLogin = (String) session.getAttribute("userLogin");

        if (userLogin == null) {
            response.sendRedirect("login.jsp?error=Please log in to update your cart");
            return;
        }

        String serialNo = request.getParameter("serialNo");
        int quantity;

        try {
            quantity = Integer.parseInt(request.getParameter("quantity"));
            if (quantity <= 0) {
                response.sendRedirect("customerCart.jsp?error=Quantity must be at least 1");
                return;
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("customerCart.jsp?error=Invalid quantity format");
            return;
        }

        try {
            // Update the item quantity in the cart
            CartManager.updateCart(userLogin, serialNo, quantity);
            response.sendRedirect("customerCart.jsp?success=Cart updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("customerCart.jsp?error=Database error");
        }
    }
}
