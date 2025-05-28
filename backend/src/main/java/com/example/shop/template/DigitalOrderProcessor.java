
package com.example.shop.template;

import com.example.shop.state.OrderContext;

public class DigitalOrderProcessor extends AbstractOrderProcessor {
    @Override
    protected void doProcess(OrderContext ctx) {
        System.out.println("=== Обработка цифрового заказа ===");
    }
}
