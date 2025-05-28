
package com.example.shop.state;

public class NewOrderState implements OrderState {
    @Override
    public void handle(OrderContext ctx) {
        System.out.println("Состояние: Новый заказ. Готовим к обработке...");
        ctx.setState(new ProcessingState());
    }
    @Override
    public String name() { return "NEW"; }
}
