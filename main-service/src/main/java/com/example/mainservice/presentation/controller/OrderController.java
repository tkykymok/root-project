package com.example.mainservice.presentation.controller;

import com.example.mainservice.application.usecase.order.*;
import com.example.mainservice.presentation.web.request.order.CreateOrderRequest;
import com.example.mainservice.presentation.web.request.order.UpdateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final FetchOrderUsecase fetchOrderUsecase;
    private final CreateOrderUsecase createOrderUsecase;
    private final UpdateOrderUsecase updateOrderUsecase;

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderDetail(@PathVariable Long orderId) throws IOException {

        return ResponseEntity.ok(fetchOrderUsecase.execute(orderId));
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {

        List<OrderItemInput> orderItems = request.orderItems().stream()
                .map(orderItem -> new OrderItemInput(
                        null,
                        orderItem.productId(),
                        orderItem.price(),
                        orderItem.quantity()
                )
        ).collect(Collectors.toList());

        CreateOrderInput input = new CreateOrderInput(1L, orderItems);
        return ResponseEntity.ok(createOrderUsecase.execute(input));
    }

    @PutMapping("/update-order/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable Long orderId,
                                         @RequestBody UpdateOrderRequest request) {

        List<OrderItemInput> orderItems = request.orderItems().stream().map(
                orderItem -> new OrderItemInput(
                        orderItem.orderItemId(),
                        orderItem.productId(),
                        orderItem.price(),
                        orderItem.quantity()
                )
        ).collect(Collectors.toList());

        UpdateOrderInput input = new UpdateOrderInput(1L, orderId, request.version(), orderItems);
        return ResponseEntity.ok(updateOrderUsecase.execute(input));
    }
}
