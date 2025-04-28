package com.example.mylogin;

public class Order {
    private String name, address, phone, customization, tshirtImage;
    private int quantity;

    public Order(String name, String address, String city, String postalCode, String phoneNumber, String customDesign, int productImage) {
        // Default constructor required for Firebase
    }

    public Order(String name, String address, String phone, String customization, String tshirtImage, int quantity) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.customization = customization;
        this.tshirtImage = tshirtImage;
        this.quantity = quantity;
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getCustomization() {
        return customization;
    }

    public String getTshirtImage() {
        return tshirtImage;
    }

    public int getQuantity() {
        return quantity;
    }
}
