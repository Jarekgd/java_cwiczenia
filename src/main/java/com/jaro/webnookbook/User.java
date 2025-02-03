package com.jaro.webnookbook;

/**
 *
 * @author Jaro
 */

/************** class user for both customer and admin login ****************/
public class User {
    
// attributes:
    private String email;
    private String userName;
    private String login;
    private String password;
    private String privilege;
    private static int autoIncrement = 1;
    
// constructor:
    public User(String email, String userName, String login, String password, String privilege) {
        this.email = email;
        this.userName = userName;
        this.login = login;
        this.password = password;
        this.privilege = privilege;
    }

    // getters methods return private variables as public:
    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getPrivilege() {
        return privilege;
    }

    // setters for assigning values to private instances:
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    // String representation of user object:
    @Override
    public String toString() {
        return "User Name: " + userName + "\nEmail: " + email + "\nLogin: " + login + "\nPrivilage: " + privilege + "\n*******************************";
    }

}
