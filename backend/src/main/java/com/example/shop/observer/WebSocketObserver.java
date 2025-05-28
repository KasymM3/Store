package com.example.shop.observer;

import com.example.shop.state.OrderContext;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import java.util.Map;

public class WebSocketObserver implements OrderObserver {
    private final SimpMessagingTemplate ws;

    public WebSocketObserver(SimpMessagingTemplate ws) {
        this.ws = ws;
    }

    @Override
    public void update(OrderContext order) {
        Map<String,Object> payload = Map.of(
                "id", order.getId(),
                "state", order.getStateName()
        );
        ws.convertAndSend("/topic/orders", payload);
        System.out.printf("[WebSocket] pushed Order #%d %s%n",
                order.getId(), order.getStateName());
    }
}
