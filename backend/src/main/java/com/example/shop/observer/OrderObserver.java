package com.example.shop.observer;

import com.example.shop.state.OrderContext;

public interface OrderObserver {
    void update(OrderContext order);
}
