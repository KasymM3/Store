package com.example.shop.command;

import com.example.shop.service.OrderService;
import com.example.shop.state.OrderContext;

public class PayOrderCommand implements OrderCommand {
    private final OrderService service;
    private final long orderId;

    public PayOrderCommand(OrderService service, long orderId) {
        this.service = service;
        this.orderId = orderId;
    }

    @Override
    public void execute() {
        OrderContext ctx = service.getById(orderId);
        if (ctx == null) return;

        // Отключаем промежуточные уведомления
        ctx.setNotificationsEnabled(false);


        service.processOrder(orderId);

        // Включаем уведомления и посылаем итоговое оповещение
        ctx.setNotificationsEnabled(true);
        ctx.notifyObserversManually();
    }
}
