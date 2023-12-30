package com.example.mainservice.application.usecase.order;

import com.example.mainservice.domain.model.order.Order;
import com.example.mainservice.domain.model.order.OrderItem;
import com.example.mainservice.domain.model.valueobject.*;
import com.example.mainservice.domain.repository.order.OrderRepository;
import com.example.mainservice.domain.service.ProductService;
import com.example.shared.application.usecase.Usecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateOrderUsecase extends Usecase<UpdateOrderInput, Void> {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Override
    @Transactional
    public Void execute(UpdateOrderInput input) {
        // 注文IDを使用して、注文を検索する
        Order order = orderRepository.findByIdAndVersion(OrderId.of(input.orderId()), VersionKey.of(input.version()));
        if (order == null) {
            throw new RuntimeException("楽観的排他制御エラー");
        }

        // 注文のバージョンをインクリメントする
        order.updateVersion(VersionKey.of(input.version()));

        // 注文アイテムをクリアして、新しいアイテムを追加する
        order.clearOrderItems();
        addOrderItems(order, input.orderItems());

        // 注文を更新する
        orderRepository.update(order);

        return null;
    }

    // 依存関係をチェックする
    private void checkDependencies(CreateOrderInput input) {
        // 商品ID存在チェック
        for (OrderItemInput orderItem : input.orderItems()) {
            if (!productService.isProductValid(ProductId.of(orderItem.productId()))) {
                throw new RuntimeException("商品IDが不正です");
            }
        }
    }

    // 注文アイテムを追加する
    private void addOrderItems(Order order, List<OrderItemInput> orderItems) {
        for (OrderItemInput orderItem : orderItems) {
            OrderItem newOrderItem = OrderItem.create(
                    OrderId.of(order.getId().value()),
                    SeqNo.of(order.getOrderItems().size() + 1),
                    ProductId.of(orderItem.productId()),
                    Price.of(orderItem.price()),
                    Quantity.of(orderItem.quantity())
            );
            order.addOrderItem(newOrderItem);
        }
    }
}