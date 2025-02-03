package com.jaro.webnookbook;

public class Order {
    private int orderId;
    private String userLogin;
    private double totalAmount;
    private String orderDate;

    public Order(int orderId, String userLogin, double totalAmount, String orderDate) {
        this.orderId = orderId;
        this.userLogin = userLogin;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
    }


    public int getOrderId() { return orderId; }
    public String getUserLogin() { return userLogin; }
    public double getTotalAmount() { return totalAmount; }
    public String getOrderDate() { return orderDate; }


    public void setOrderId(int orderId) { this.orderId = orderId; }
    public void setUserLogin(String userLogin) { this.userLogin = userLogin; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
}
