package com.example.shop.dto;

public class CreateOrderRequest {

    private String type;

    private double price;


    public CreateOrderRequest() {}

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
