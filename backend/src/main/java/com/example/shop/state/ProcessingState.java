
package com.example.shop.state;

public class ProcessingState implements OrderState {
    @Override
    public void handle(OrderContext ctx) {
        System.out.println("Состояние: В обработке. Упаковываем...");
        ctx.setState(new ShippedState());
    }
    @Override
    public String name() { return "PROCESSING"; }
}
