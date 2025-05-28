package com.example.shop.state;

import com.example.shop.observer.OrderObserver;
import java.util.*;
import java.time.Instant;


public class OrderContext {
    private final long id;
    private final String type;
    private final double basePrice;
    private double deliveryCost;
    private int estimatedDays;
    private final Instant createdAt;


    private OrderState state;
    private final List<OrderObserver> observers = new ArrayList<>();
    private boolean notificationsEnabled = true;

    public OrderContext(long id, String type, double basePrice) {
        this.id = id;
        this.type = type.toLowerCase();
        this.basePrice = basePrice;
        this.createdAt = Instant.now();
        this.deliveryCost = 0;
        this.estimatedDays = 0;
        this.state = new NewOrderState();
    }

    public void next() {
        System.out.printf(">>> Order #%d in state %s%n", id, state.name());
        state.handle(this);
        if (notificationsEnabled) {
            notifyObservers();
        }
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void addObserver(OrderObserver o)    { observers.add(o); }

    private void notifyObservers() {
        observers.forEach(o -> o.update(this));
    }

    public void notifyObserversManually() {
        notifyObservers();
    }

    public void setNotificationsEnabled(boolean enabled) {
        this.notificationsEnabled = enabled;
    }

    public long getId()               { return id; }
    public String getType()           { return type; }
    public double getBasePrice()      { return basePrice; }
    public double getDeliveryCost()   { return deliveryCost; }
    public void setDeliveryCost(double c)   { this.deliveryCost = c; }
    public int getEstimatedDays()     { return estimatedDays; }
    public void setEstimatedDays(int d)     { this.estimatedDays = d; }
    public String getStateName()      { return state.name(); }
    public OrderState getState()      { return state; }
    public void setState(OrderState s){ this.state = s; }

    public void printReceipt() {
        System.out.println("\n=== Receipt for Order #" + id + " ===");
        System.out.println("Base price:  " + basePrice);
        System.out.println("Delivery:    " + deliveryCost);
        System.out.println("Total:       " + (basePrice + deliveryCost));
        System.out.println("ETA days:    " + estimatedDays);
        System.out.println("==================================\n");
    }
}
