package com.example.shop.controller;

import com.example.shop.command.CommandInvoker;
import com.example.shop.command.OrderCommand;
import com.example.shop.command.PayOrderCommand;
import com.example.shop.command.CancelOrderCommand;
import com.example.shop.command.RefundOrderCommand;
import com.example.shop.dto.CreateOrderRequest;
import com.example.shop.dto.OrderDto;
import com.example.shop.state.OrderContext;
import com.example.shop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {
    private final OrderService service;
    private final CommandInvoker invoker;

    public OrderRestController(OrderService service, CommandInvoker invoker) {
        this.service = service;
        this.invoker = invoker;
    }

    // GET /api/orders
    @GetMapping
    public List<OrderDto> list() {
        return service.getAll().stream()
                .map(service::toDto)
                .collect(Collectors.toList());
    }

    // GET /api/orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable Long id) {
        OrderContext ctx = service.getById(id);
        return ctx != null
                ? ResponseEntity.ok(service.toDto(ctx))
                : ResponseEntity.notFound().build();
    }

    // POST /api/orders
    @PostMapping
    public ResponseEntity<OrderDto> create(@RequestBody CreateOrderRequest dto) {
        long id = service.createOrder(dto.getType(), dto.getPrice());
        OrderContext ctx = service.getById(id);
        return ResponseEntity.ok(service.toDto(ctx));
    }


    // POST /api/orders/{id}/action?action=pay|cancel|refund
    @PostMapping("/{id}/action")
    public ResponseEntity<Void> action(
            @PathVariable Long id,
            @RequestParam String action
    ) {
        OrderCommand cmd;
        switch (action) {
            case "pay":
                cmd = new PayOrderCommand(service, id);
                break;
            case "cancel":
                cmd = new CancelOrderCommand(service, id);
                break;
            case "refund":
                cmd = new RefundOrderCommand(service, id);
                break;
            default:
                return ResponseEntity.badRequest().build();
        }
        invoker.executeCommand(cmd);
        return ResponseEntity.ok().build();
    }
}
