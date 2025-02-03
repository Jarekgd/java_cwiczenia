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
        String quantityParam = request.getParameter("quantity");


        if (serialNo == null || serialNo.isEmpty() || productType == null || productType.isEmpty() || quantityParam == null || quantityParam.isEmpty()) {
            response.sendRedirect("customerDashboard.jsp?error=Invalid item or quantity");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityParam);
            if (quantity <= 0) {
                response.sendRedirect("customerDashboard.jsp?error=Quantity must be at least 1");
                return;
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("customerDashboard.jsp?error=Invalid quantity format");
            return;
        }

        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("customerDashboard.jsp?error=Database error");
        }
    }
}
