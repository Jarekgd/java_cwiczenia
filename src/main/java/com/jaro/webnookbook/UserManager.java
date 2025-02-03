package com.jaro.webnookbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import static javax.swing.UIManager.getString;

/**
 * UserManager class for handling user-related operations
 */
public class UserManager {
    private static final String DB_URL = "jdbc:sqlite:C:\\webnookbook\\sqlite\\nookbook.db";

   public static ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "SELECT u.email, u.userName, u.login, r.roleName " +
                             "FROM users u JOIN roles r ON u.privilege = r.roleId";
                try (PreparedStatement pstmt = connection.prepareStatement(sql);
                     ResultSet rs = pstmt.executeQuery()) {

                    while (rs.next()) {
                        users.add(new User(
                            rs.getString("email"),
                            rs.getString("userName"),
                            rs.getString("login"),
                            null,  // Not returning password for security
                            rs.getString("roleName")  // Role: "Admin" or "Customer"
                        ));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public static User authenticateUser(String login, String password) {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String sql = "SELECT u.email, u.userName, u.login, u.password, r.roleName " +
                             "FROM users u JOIN roles r ON u.privilege = r.roleId " +
                             "WHERE u.login = ?";

                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setString(1, login);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            String storedPassword = rs.getString("password");
                            String storedRole = rs.getString("roleName");

                            // Debugging logs
                            System.out.println("Login Attempt: " + login + " | Role: " + storedRole);

                            if (storedPassword.equals(password)) {  
                                return new User(
                                    rs.getString("email"),
                                    rs.getString("userName"),
                                    rs.getString("login"),
                                    rs.getString("password"),
                                    storedRole
                                );
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;  // Return null if login fails
    }
    public static User getUserById(int userId) {
    User user = null;
    try {
        Class.forName("org.sqlite.JDBC");
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT usrId, email, userName, login, password, roles.roleName FROM users " +
                         "JOIN roles ON users.privilege = roles.roleId WHERE usrId = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        user = new User(
                            rs.getString("email"),
                            rs.getString("userName"),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getString("roleName")
                        );
                    }
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return user;
}




}
