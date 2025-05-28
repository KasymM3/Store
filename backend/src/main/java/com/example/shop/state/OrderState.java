package com.example.shop.state;

public interface OrderState {
    void handle(OrderContext context);

    String name();
}