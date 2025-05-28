
package com.example.shop.template;

import com.example.shop.state.OrderContext;
import com.example.shop.state.NewOrderState;

public abstract class AbstractOrderProcessor {

    public final void process(OrderContext ctx) {

        if (ctx.getState() instanceof NewOrderState) {

            doProcess(ctx);

            validate(ctx);

            calculateAndPrint(ctx);
        }

        postProcess(ctx);
    }

    protected void validate(OrderContext ctx) {
        System.out.printf("Validating %s order #%d%n",
                ctx.getType(), ctx.getId());
    }





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






    protected abstract void doProcess(OrderContext ctx);

    
    protected void postProcess(OrderContext ctx) {
        ctx.next();
    }
}
