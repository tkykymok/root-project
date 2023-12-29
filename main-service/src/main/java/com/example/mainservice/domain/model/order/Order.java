package com.example.mainservice.domain.model.order;

import com.example.mainservice.domain.model.AggregateRoot;
import com.example.mainservice.domain.model.valueobject.Amount;
import com.example.mainservice.domain.model.valueobject.OrderId;
import com.example.mainservice.domain.model.valueobject.UserId;
import com.example.mainservice.domain.model.valueobject.VersionKey;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Order extends AggregateRoot<OrderId> {
    private UserId userId;
    private LocalDate orderDate;
    private Amount totalAmount;
    private VersionKey version;
    private List<OrderItem> orderItems;

    private Order() {
    }

    // DBから取得したデータをドメインオブジェクトに変換する
    public static Order reconstruct(OrderId id, UserId userId, LocalDate orderDate, Amount totalAmount, VersionKey version, List<OrderItem> orderItems) {
        Order order = new Order();
        order.id = id;
        order.userId = userId;
        order.orderDate = orderDate;
        order.totalAmount = totalAmount;
        order.version = version;
        order.orderItems = orderItems;
        return order;
    }

    // ファクトリメソッド
    public static Order create(UserId userId) {
        Order order = new Order();
        order.userId = userId;
        order.orderDate = LocalDate.now();
        order.totalAmount = Amount.of(BigDecimal.ZERO);
        order.version = VersionKey.of(0L);
        order.orderItems = new ArrayList<>();
        return order;
    }

    // バージョンを更新する
    public void updateVersion(VersionKey version) {
        this.version = version.increment();
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        calculateTotalAmount();
    }

    // 注文アイテムを空にする
    public void clearOrderItems() {
        this.orderItems.clear();
    }

    // 合計金額を計算する
    private void calculateTotalAmount() {
        this.totalAmount = Amount.of(BigDecimal.ZERO);
        for (OrderItem orderItem : this.orderItems) {
            this.totalAmount = this.totalAmount.add(orderItem.getSubTotalAmount());
        }
    }

}