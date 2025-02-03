package com.jaro.webnookbook;

public class OrderItem {
    private String serialNo;
    private String name;
    private double price;
    private int quantity;

    public OrderItem(String serialNo, String name, double price, int quantity) {
        this.serialNo = serialNo;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getSerialNo() { return serialNo; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }


    public void setQuantity(int quantity) { this.quantity = quantity; }
}
