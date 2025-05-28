package com.example.shop.factory;

import com.example.shop.state.OrderContext;

public interface OrderFactory {
    OrderContext createOrder(long id, double basePrice);
}