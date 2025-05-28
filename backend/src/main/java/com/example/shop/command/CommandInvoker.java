package com.example.shop.command;

import org.springframework.stereotype.Component;
import java.util.ArrayDeque;
import java.util.Deque;

@Component
public class CommandInvoker {
    private final Deque<OrderCommand> history = new ArrayDeque<>();

    public void executeCommand(OrderCommand cmd) {
        cmd.execute();
        history.push(cmd);
    }

    public void undo() {
        if (!history.isEmpty()) {
            System.out.println("[Invoker] undo not implemented");
        }
    }
}
