package com.example.mainservice.domain.model.order;

import com.example.mainservice.domain.model.valueobject.*;
import com.example.shared.domain.model.CompositeKeyBaseEntity;
import lombok.Getter;

@Getter
public class OrderItem extends CompositeKeyBaseEntity<OrderItemKey> {
    private ProductId productId;
    private Price price;
    private Quantity quantity;
    private Amount subTotalAmount;

    private OrderItem() {
    }

    // DBから取得したデータをドメインオブジェクトに変換する
    public static OrderItem reconstruct(OrderId orderId, SeqNo seqNo, ProductId productId, Price price, Quantity quantity, Amount subTotalAmount) {
        OrderItem orderItem = new OrderItem();
        orderItem.key = OrderItemKey.of(orderId, seqNo);
        orderItem.productId = productId;
        orderItem.price = price;
        orderItem.quantity = quantity;
        orderItem.subTotalAmount = subTotalAmount;
        return orderItem;
    }

    // ファクトリメソッド
    public static OrderItem create(OrderId orderId, SeqNo seqNo, ProductId productId, Price price, Quantity quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.key = OrderItemKey.of(orderId, seqNo);
        orderItem.productId = productId;
        orderItem.price = price;
        orderItem.quantity = quantity;
        orderItem.subTotalAmount = Amount.calculateTotalFromPriceAndQuantity(price, quantity);
        return orderItem;
    }
}

