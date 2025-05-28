package com.example.shop.observer;

import com.example.shop.state.OrderContext;
import java.time.LocalDateTime;

public class LogObserver implements OrderObserver {
    @Override
    public void update(OrderContext order) {
        System.out.printf("[Log %s] Order #%d → %s%n",
                LocalDateTime.now(), order.getId(), order.getStateName());
    }
}
