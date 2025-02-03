package com.jaro.webnookbook;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String userIdParam = request.getParameter("id");
            if (userIdParam == null || userIdParam.isEmpty()) {
                response.sendRedirect("manageUsers.jsp?error=Invalid user ID");
                return;
            }

            int userId = Integer.parseInt(userIdParam);
            User user = UserManager.getUserById(userId);

            if (user == null) {
                response.sendRedirect("manageUsers.jsp?error=User not found");
                return;
            }

            request.setAttribute("userId", userId);
            request.setAttribute("user", user);
            request.getRequestDispatcher("editUser.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("manageUsers.jsp?error=Database error");
        }
    }
}
