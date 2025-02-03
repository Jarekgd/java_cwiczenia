package com.jaro.webnookbook;

public class CartItem {
    private String serialNo;
    private String name;
    private double price;
    private int quantity;

    public CartItem(String serialNo, String name, double price, int quantity) {
        this.serialNo = serialNo;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // ✅ Getters
    public String getSerialNo() { return serialNo; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    // ✅ Setters
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
