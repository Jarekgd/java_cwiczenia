package com.jaro.webnookbook;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = UserManager.authenticateUser(login, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userRole", user.getPrivilege());
            session.setAttribute("userLogin", user.getLogin());

            if ("Admin".equalsIgnoreCase(user.getPrivilege())) {
                response.sendRedirect("adminDashboard.jsp");
            } else {
                response.sendRedirect("customerDashboard.jsp");
            }
        } else {
            response.sendRedirect("login.jsp?error=Invalid login or password");
        }
    }
}
