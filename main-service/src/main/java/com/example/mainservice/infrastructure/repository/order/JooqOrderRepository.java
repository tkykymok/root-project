package com.example.mainservice.infrastructure.repository.order;

import com.example.mainservice.domain.model.order.Order;
import com.example.mainservice.domain.model.order.OrderItem;
import com.example.mainservice.domain.model.valueobject.*;
import com.example.mainservice.domain.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.mainservice.infrastructure.jooq.Tables.ORDERS;
import static com.example.mainservice.infrastructure.jooq.Tables.ORDER_ITEMS;

@Repository
@RequiredArgsConstructor
public class JooqOrderRepository implements OrderRepository {

    private final DSLContext dsl;

    /**
     * OrderId で検索する
     *
     * @param id OrderId
     * @return Order
     */
    @Override
    public Order findById(OrderId id) {
        return findOrderAndItems(id, null);
    }

    /**
     * OrderId と VersionKey で検索する
     *
     * @param id      OrderId
     * @param version VersionKey
     * @return Order
     */
    @Override
    public Order findByIdAndVersion(OrderId id, VersionKey version) {
        return findOrderAndItems(id, version);
    }

    /**
     * Order と OrderItems を挿入する
     *
     * @param order Order
     */
    @Override
    public void insert(Order order) {
        // Order テーブルへの挿入と生成されたIDの取得
        Record insertedOrderRecord = dsl.insertInto(ORDERS)
                .set(ORDERS.USER_ID, order.getUserId().value())
                .set(ORDERS.ORDER_DATE, order.getOrderDate())
                .set(ORDERS.TOTAL_AMOUNT, order.getTotalAmount().value())
                .set(ORDERS.VERSION, order.getVersion().value())
                .returning(ORDERS.ID) // オートインクリメントされたIDを返す
                .fetchOne();

        if (insertedOrderRecord == null) {
            throw new RuntimeException("Order insert failed");
        }

        OrderId generatedId = OrderId.of(insertedOrderRecord.getValue(ORDERS.ID));
        order.setId(generatedId); // 生成されたIDをセット

        // OrderItem のリストをループして、それぞれを ORDER_ITEMS テーブルに挿入
        for (OrderItem item : order.getOrderItems()) {
            dsl.insertInto(ORDER_ITEMS)
                    .set(ORDER_ITEMS.ORDER_ID, generatedId.value())
                    .set(ORDER_ITEMS.SEQ_NO, item.getKey().seqNo().value())
                    .set(ORDER_ITEMS.PRODUCT_ID, item.getProductId().value())
                    .set(ORDER_ITEMS.PRICE, item.getPrice().value())
                    .set(ORDER_ITEMS.QUANTITY, item.getQuantity().value())
                    .set(ORDER_ITEMS.SUB_TOTAL_AMOUNT, item.getSubTotalAmount().value())
                    .execute();
        }
    }

    /**
     * Order と OrderItems を更新する
     *
     * @param order Order
     */
    @Override
    public void update(Order order) {
        // ORDERS テーブルのレコードを更新
        dsl.update(ORDERS)
                .set(ORDERS.USER_ID, order.getUserId().value())
                .set(ORDERS.ORDER_DATE, order.getOrderDate())
                .set(ORDERS.TOTAL_AMOUNT, order.getTotalAmount().value())
                .set(ORDERS.VERSION, order.getVersion().value())
                .where(ORDERS.ID.eq(order.getId().value()))
                .execute();

        // 関連するすべてのOrderItemsを削除
        dsl.deleteFrom(ORDER_ITEMS)
                .where(ORDER_ITEMS.ORDER_ID.eq(order.getId().value()))
                .execute();

        // 新しいOrderItemsを挿入
        for (OrderItem item : order.getOrderItems()) {
            dsl.insertInto(ORDER_ITEMS)
                    .set(ORDER_ITEMS.ORDER_ID, item.getKey().orderId().value())
                    .set(ORDER_ITEMS.SEQ_NO, item.getKey().seqNo().value())
                    .set(ORDER_ITEMS.PRODUCT_ID, item.getProductId().value())
                    .set(ORDER_ITEMS.PRICE, item.getPrice().value())
                    .set(ORDER_ITEMS.QUANTITY, item.getQuantity().value())
                    .set(ORDER_ITEMS.SUB_TOTAL_AMOUNT, item.getSubTotalAmount().value())
                    .execute();
        }
    }

    /**
     * Order と OrderItems を取得する
     *
     * @param id      OrderId
     * @param version VersionKey
     * @return Order
     */
    private Order findOrderAndItems(OrderId id, VersionKey version) {
        Record orderRecord = fetchOrderRecord(id, version);
        if (orderRecord == null) {
            return null;
        }

        Result<Record> orderItemsRecords = dsl.select()
                .from(ORDER_ITEMS)
                .where(ORDER_ITEMS.ORDER_ID.eq(id.value()))
                .fetch();

        return recordToOrder(orderRecord, orderItemsRecords);
    }

    /**
     * Order テーブルからレコードを取得する
     *
     * @param id      OrderId
     * @param version VersionKey
     * @return Record
     */
    private Record fetchOrderRecord(OrderId id, VersionKey version) {
        var selectCondition = dsl.select()
                .from(ORDERS)
                .where(ORDERS.ID.eq(id.value()));

        if (version != null) {
            selectCondition = selectCondition.and(ORDERS.VERSION.eq(version.value()));
        }

        return selectCondition.fetchOne();
    }

    /**
     * Record から Order オブジェクトを生成する
     *
     * @param orderRecord       Order テーブルのレコード
     * @param orderItemsRecords OrderItems テーブルのレコード
     * @return Order
     */
    private Order recordToOrder(Record orderRecord, Result<Record> orderItemsRecords) {
        OrderId id = OrderId.of(orderRecord.get(ORDERS.ID));
        UserId userId = UserId.of(orderRecord.get(ORDERS.USER_ID));
        LocalDate orderDate = orderRecord.get(ORDERS.ORDER_DATE);
        Amount totalAmount = Amount.of(orderRecord.get(ORDERS.TOTAL_AMOUNT));
        VersionKey version = VersionKey.of(orderRecord.get(ORDERS.VERSION));

        // OrderItemsの変換
        List<OrderItem> orderItems = orderItemsRecords.stream()
                .map(this::recordToOrderItem)
                .collect(Collectors.toList());

        return Order.reconstruct(id, userId, orderDate, totalAmount, version, orderItems);
    }

    /**
     * Record から OrderItem オブジェクトを生成する
     *
     * @param record OrderItems テーブルのレコード
     * @return OrderItem
     */
    private OrderItem recordToOrderItem(Record record) {
        OrderId orderId = OrderId.of(record.get(ORDER_ITEMS.ORDER_ID));
        SeqNo seqNo = SeqNo.of(record.get(ORDER_ITEMS.SEQ_NO));
        ProductId productId = ProductId.of(record.get(ORDER_ITEMS.PRODUCT_ID));
        Price price = Price.of(record.get(ORDER_ITEMS.PRICE));
        Quantity quantity = Quantity.of(record.get(ORDER_ITEMS.QUANTITY));
        Amount subTotalAmount = Amount.of(record.get(ORDER_ITEMS.SUB_TOTAL_AMOUNT));

        return OrderItem.reconstruct(orderId, seqNo, productId, price, quantity, subTotalAmount);

    }
}
