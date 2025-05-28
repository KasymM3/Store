package com.example.shop.observer;

import com.example.shop.state.OrderContext;

public class SMSObserver implements OrderObserver {
    @Override
    public void update(OrderContext order) {
        System.out.printf("[SMS] Order #%d → %s%n",
                order.getId(), order.getStateName());
    }
}
