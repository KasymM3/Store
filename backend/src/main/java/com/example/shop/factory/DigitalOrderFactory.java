package com.example.shop.factory;

import com.example.shop.state.OrderContext;

public class DigitalOrderFactory implements OrderFactory {
    @Override
    public OrderContext createOrder(long id, double basePrice) {
        return new OrderContext(id, "Digital", basePrice);
    }
}
