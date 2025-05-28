
package com.example.shop.state;

public class DeliveredState implements OrderState {
    @Override
    public void handle(OrderContext ctx) {
        System.out.println("Состояние: Доставлен. Финализация...");

        ctx.setDeliveryCost( (ctx.getType().equals("physical")) ? 10.0 : 0.0 );
        ctx.setEstimatedDays( (ctx.getType().equals("physical")) ? 5 : 0 );
        ctx.printReceipt();
        ctx.setState(new ClosedState());
    }
    @Override
    public String name() { return "DELIVERED"; }
}
