package com.example.shop.service;

import com.example.shop.dto.OrderDto;
import com.example.shop.factory.OrderFactory;
import com.example.shop.factory.PhysicalOrderFactory;
import com.example.shop.factory.DigitalOrderFactory;
import com.example.shop.state.OrderContext;
import com.example.shop.state.ClosedState;
import com.example.shop.template.AbstractOrderProcessor;
import com.example.shop.template.PhysicalOrderProcessor;
import com.example.shop.template.DigitalOrderProcessor;
import com.example.shop.observer.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {
    private final Map<Long, OrderContext> orders = new LinkedHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    private final OrderFactory physicalFactory = new PhysicalOrderFactory();
    private final OrderFactory digitalFactory  = new DigitalOrderFactory();

//    @Autowired private JavaMailSender mailSender;
    @Autowired private SimpMessagingTemplate wsTemplate;

    private final AbstractOrderProcessor physicalProcessor = new PhysicalOrderProcessor();
    private final AbstractOrderProcessor digitalProcessor  = new DigitalOrderProcessor();


    public long createOrder(String type, double basePrice) {
        long id = seq.getAndIncrement();

        // выбираем фабрику по типу
        OrderContext ctx;
        if ("physical".equals(type)) {
            ctx = physicalFactory.createOrder(id, basePrice);
        } else {
            ctx = digitalFactory.createOrder(id, basePrice);
        }

        ctx.addObserver(new SMSObserver());
        ctx.addObserver(new LogObserver());
//        ctx.addObserver(new EmailObserver(mailSender, "customer@example.com"));
        ctx.addObserver(new WebSocketObserver(wsTemplate));

        orders.put(id, ctx);
        return id;
    }

    public OrderContext getById(long id) {
        return orders.get(id);
    }

    public List<OrderContext> getAll() {
        return new ArrayList<>(orders.values());
    }


    public void processNextStep(long id) {
        OrderContext ctx = orders.get(id);
        if (ctx == null) return;

        if ("physical".equals(ctx.getType())) {
            physicalProcessor.process(ctx);
        } else {
            digitalProcessor.process(ctx);
        }
    }

    public void processOrder(long id) {
        OrderContext ctx = orders.get(id);
        if (ctx == null) return;
        while (!(ctx.getState() instanceof ClosedState)) {
            processNextStep(id);
        }
    }

    public void cancelOrder(long id) {
        orders.remove(id);
    }

    public void refundOrder(long id) {
        orders.remove(id);
    }

    public OrderDto toDto(OrderContext ctx) {
        OrderDto dto = new OrderDto();
        dto.setId(ctx.getId());
        dto.setType(ctx.getType());
        dto.setBasePrice(ctx.getBasePrice());
        dto.setState(ctx.getState().getClass().getSimpleName());
        dto.setCreatedAt(ctx.getCreatedAt());
        dto.setDeliveryCost(ctx.getDeliveryCost());
        dto.setEstimatedDays(ctx.getEstimatedDays());
        return dto;
    }
}
