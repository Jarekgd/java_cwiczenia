package com.jaro.webnookbook;

public class Order {
    private int orderId;
    private String userLogin;
    private double totalAmount;
    private String orderDate;
    private String status;


    public Order(int orderId, String userLogin, double totalAmount, String orderDate, String status) {
        this.orderId = orderId;
        this.userLogin = userLogin;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
    }


    public Order(int orderId, String userLogin, double totalAmount, String orderDate) {
        this.orderId = orderId;
        this.userLogin = userLogin;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = "Pending"; // Default status
    }


    public int getOrderId() { return orderId; }
    public String getUserLogin() { return userLogin; }
    public double getTotalAmount() { return totalAmount; }
    public String getOrderDate() { return orderDate; }
    public String getStatus() { return status; }


    public void setStatus(String status) { this.status = status; }
}
