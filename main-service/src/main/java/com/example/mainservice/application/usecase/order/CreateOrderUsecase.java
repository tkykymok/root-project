package com.example.mainservice.application.usecase.order;

import com.example.mainservice.domain.event.order.OrderCreatedEvent;
import com.example.mainservice.domain.event.order.OrderCreatedEventPublisher;
import com.example.mainservice.domain.model.order.Order;
import com.example.mainservice.domain.model.order.OrderItem;
import com.example.mainservice.domain.model.valueobject.*;
import com.example.mainservice.domain.repository.order.OrderRepository;
import com.example.mainservice.domain.service.ProductValidator;
import com.example.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateOrderUsecase extends Usecase<CreateOrderInput, Void> {

    private final OrderRepository orderRepository;
    private final ProductValidator productValidator;
    private final OrderCreatedEventPublisher orderCreatedEventPublisher;


    @Override
    @Transactional
    public Void execute(CreateOrderInput input) {
        // 依存関係をチェックする
        checkDependencies(input);

        // 注文を生成する
        Order order = Order.create(new UserId(input.userId()));

        // 注文アイテムを追加する
        addOrderItems(order, input.orderItems());

        // 注文を保存する
        orderRepository.insert(order);

        // 注文作成イベントを発行する
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(order, ZonedDateTime.now());
        orderCreatedEventPublisher.publish(orderCreatedEvent);

        return null;
    }


    // 依存関係をチェックする
    private void checkDependencies(CreateOrderInput input) {
        List<ProductId> productIds = input.orderItems().stream()
                .map(orderItem -> ProductId.of(orderItem.productId()))
                .collect(Collectors.toList());
        productValidator.validateProductIds(productIds);
    }

    // 注文アイテムを追加する
    private void addOrderItems(Order order, List<OrderItemInput> orderItems) {
        for (OrderItemInput orderItem : orderItems) {
            OrderItem newOrderItem = OrderItem.create(
                    null,
                    SeqNo.of(order.getOrderItems().size() + 1),
                    ProductId.of(orderItem.productId()),
                    Price.of(orderItem.price()),
                    Quantity.of(orderItem.quantity())
            );
            order.addOrderItem(newOrderItem);
        }
    }
}