
package com.example.shop.state;

public class ClosedState implements OrderState {
    @Override
    public void handle(OrderContext ctx) {
        System.out.println("Состояние: Закрыт. Заказ #" + ctx.getId() + " завершён.");
    }
    @Override
    public String name() { return "CLOSED"; }
}
