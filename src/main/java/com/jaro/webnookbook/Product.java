package com.jaro.webnookbook;

/**
 *
 * @author Jaro
 */
//Abstract class Product, not to be used directly, only to be extended by Book and Accessory classes
public abstract class Product {

//    private atributes for security - can be called only within the same class
    private static int autoIncrement = 1;
    private int id;
    private String serialNo;
    private String name;
    private double price;
    private int quantity;

//    constructor - method to initialise attributes with values as arguments
    public Product(String serialNo, String name, double price, int quantity) {  // new instance Product
        this.id = autoIncrement++;
        this.serialNo = serialNo;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

//Getters - methods which allow access to attributes values
    public static int getAutoIncrement() {
        return autoIncrement;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    
//    Setters - methods that allow to modify attributes values

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static void setAutoIncrement(int autoIncrement) {
        Product.autoIncrement = autoIncrement;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

//    String representation of the Product object - used to show accessories
    @Override
    public String toString() {
        return "ID: " + id + ", Serial Number: " + serialNo + ", Name: " + name
                + ", Price: $" + price + ", Quantity: " + quantity;
    }

}
