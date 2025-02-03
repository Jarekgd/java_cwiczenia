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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userLogin = (String) session.getAttribute("userLogin");

        if (userLogin == null) {
            response.sendRedirect("login.jsp?error=Please log in to update your cart");
            return;
        }

        String serialNo = request.getParameter("serialNo");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        if (quantity > 0) {
            CartManager.updateCart(userLogin, serialNo, quantity);
            response.sendRedirect("customerCart.jsp?success=Cart updated successfully");
        } else {
            response.sendRedirect("customerCart.jsp?error=Quantity must be at least 1");
        }
    }
}

