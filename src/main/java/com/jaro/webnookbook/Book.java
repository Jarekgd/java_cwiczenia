package com.jaro.webnookbook;

/**
 *
 * @author jaro
 */

//Book class based on product class:
public class Book extends Product {
//    additional attributes not existing Product class
    private String author;
    private Category category;
    
    public Book(String serialNo, String name, String author, double price, int quantity,  Category category){
        super(serialNo, name, price, quantity);
        this.author = author;
        this.category = category;
    }

//    Getters and setters for attributes not initialised in Product class:
    public String getAuthor() {
        return author;
    }

    public Category getCategory() {
        return category;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    
}
