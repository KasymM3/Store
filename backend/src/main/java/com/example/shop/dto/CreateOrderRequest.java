package com.example.shop.dto;

public class CreateOrderRequest {
    // тип заказа: "digital" или "physical"
    private String type;
    // базовая цена
    private double price;

    // обязательно пустой конструктор для Jackson
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
