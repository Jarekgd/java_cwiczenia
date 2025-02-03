package com.jaro.webnookbook;

public class Book extends Product {
    private String author;
    private Category category; 

    public Book(String serialNo, String name, String author, double price, int quantity, Category category) {
        super(serialNo, name, price, quantity);
        this.author = author;
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getCategoryId() {
        return (category != null) ? category.getId() : -1; // Returns -1 if category is null
    }
}
