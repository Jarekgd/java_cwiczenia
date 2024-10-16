package com.jaro.oop;

public class User {
    private String name;
    private int age;
    public User(String nameIn,int ageIn){
        this.name = nameIn;
        this.age=ageIn;
    }
    
    public void description(){
        System.out.printf("My name is %s. I am %d years old\n", this.name, this.age);
    }
    public String getName(){
        return this.name;
    }
    public int getAge(){
        return this.age;
    }
}
