package com.jaro.webnookbook;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userLogin = (String) session.getAttribute("userLogin");

        if (userLogin == null) {
            response.sendRedirect("login.jsp?error=Please log in to add items to your cart");
            return;
        }

        String serialNo = request.getParameter("serialNo");
        String productType = request.getParameter("productType");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        // Retrieve product details
        String productName = "";
        double productPrice = 0.0;

        if ("book".equals(productType)) {
            Book book = BookManager.getBookBySerialNo(serialNo);
            if (book != null) {
                productName = book.getName();
                productPrice = book.getPrice();
            }
        } else if ("accessory".equals(productType)) {
            Accessory accessory = AccessoryManager.getAccessoryBySerialNo(serialNo);
            if (accessory != null) {
                productName = accessory.getName();
                productPrice = accessory.getPrice();
            }
        }

        if (!productName.isEmpty()) {
            CartManager.addToCart(userLogin, serialNo, productName, productPrice, quantity);
            response.sendRedirect("customerCart.jsp?success=Item added to cart");
        } else {
            response.sendRedirect("customerDashboard.jsp?error=Item not found");
        }
    }
}

