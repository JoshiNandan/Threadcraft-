package com.example.mylogin;

public class Product {
    private String name;
    private int imageResource;
    private String description;
    private String price;

    public Product(String name, int imageResource, String description, String price) {
        this.name = name;
        this.imageResource = imageResource;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }
}
