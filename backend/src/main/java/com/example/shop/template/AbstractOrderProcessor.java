
package com.example.shop.template;

import com.example.shop.state.OrderContext;
import com.example.shop.state.NewOrderState;

public abstract class AbstractOrderProcessor {

    public final void process(OrderContext ctx) {
        // печатаем шаблонный блок только на NEW
        if (ctx.getState() instanceof NewOrderState) {
            // 1) заголовок (из конкретного процессора)
            doProcess(ctx);
            // 2) валидация
            validate(ctx);
            // 3) расчёт + печать стоимости
            calculateAndPrint(ctx);
        }
        // 4) переключаем состояние всегда
        postProcess(ctx);
    }

    protected void validate(OrderContext ctx) {
        System.out.printf("Validating %s order #%d%n",
                ctx.getType(), ctx.getId());
    }


//     * Считаем deliveryCost, estimatedDays
//     * и сразу печатаем "Digital order cost: base=..., delivery=..., days=..."
//
    private void calculateAndPrint(OrderContext ctx) {
        if ("physical".equals(ctx.getType())) {
            ctx.setDeliveryCost(10.0);
            ctx.setEstimatedDays(5);
        } else {
            ctx.setDeliveryCost(0.0);
            ctx.setEstimatedDays(0);
        }
        String typeCap = ctx.getType().substring(0,1).toUpperCase()
                + ctx.getType().substring(1);
        System.out.printf("%s order cost: base=%.2f, delivery=%.2f, days=%d%n",
                typeCap, ctx.getBasePrice(),
                ctx.getDeliveryCost(), ctx.getEstimatedDays());
    }

//     Заголовок, конкретная строка вида
//      === Обработка физического заказа ===
//      или
//     === Обработка цифрового заказа ===

    protected abstract void doProcess(OrderContext ctx);

    /** Переводим в следующий `State` */
    protected void postProcess(OrderContext ctx) {
        ctx.next();
    }
}
