
package com.example.shop.template;

import com.example.shop.state.OrderContext;

public class PhysicalOrderProcessor extends AbstractOrderProcessor {
    @Override
    protected void doProcess(OrderContext ctx) {
        System.out.println("=== Обработка физического заказа ===");
    }
}
