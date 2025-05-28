
package com.example.shop.state;

public class ShippedState implements OrderState {
    @Override
    public void handle(OrderContext ctx) {
        System.out.println("Состояние: Отправлен. В пути...");
        ctx.setState(new DeliveredState());
    }
    @Override
    public String name() { return "SHIPPED"; }
}
