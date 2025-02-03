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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userLogin = (String) session.getAttribute("userLogin");

        if (userLogin == null) {
            response.sendRedirect("login.jsp?error=Please log in to modify your cart");
            return;
        }

        String serialNo = request.getParameter("serialNo");

        if (serialNo != null) {
            CartManager.removeFromCart(userLogin, serialNo);
            response.sendRedirect("customerCart.jsp?success=Item removed successfully");
        } else {
            response.sendRedirect("customerCart.jsp?error=Invalid item");
        }
    }
}

