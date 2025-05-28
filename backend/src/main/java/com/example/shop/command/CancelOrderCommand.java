package com.example.shop.command;

import com.example.shop.service.OrderService;

public class CancelOrderCommand implements OrderCommand {
    private final OrderService service;
    private final long orderId;

    public CancelOrderCommand(OrderService service, long orderId) {
        this.service = service;
        this.orderId = orderId;
    }

    @Override
    public void execute() {
        service.cancelOrder(orderId);
    }
}
