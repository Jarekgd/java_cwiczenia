package com.jaro.webnookbook;

import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CustomerOrdersServlet")
public class CustomerOrdersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String userLogin = (String) session.getAttribute("userLogin");

        //If user is not logged in, redirect to login page
        if (userLogin == null) {
            response.sendRedirect("login.jsp?error=Please log in to view orders");
            return;
        }

        //Fetch user orders
        ArrayList<Order> orders = OrderManager.getUserOrders(userLogin);

        //Attach orders to request scope
        request.setAttribute("orders", orders);
        
        //Forward request to JSP page
        request.getRequestDispatcher("customerOrders.jsp").forward(request, response);
    }
}
