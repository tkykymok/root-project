package com.example.mainservice.application.usecase.order;

import com.example.mainservice.domain.model.order.Order;
import com.example.mainservice.domain.model.valueobject.OrderId;
import com.example.mainservice.domain.queryservice.order.OrderDetailsResult;
import com.example.mainservice.domain.queryservice.order.OrderQueryService;
import com.example.mainservice.domain.repository.order.OrderRepository;
import com.example.shared.application.usecase.Usecase;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FetchOrderUsecase extends Usecase<Long, FetchOrderOutput> {

    private final OrderQueryService orderQueryService;
    private final OrderRepository orderRepository;

    public FetchOrderUsecase(OrderQueryService orderQueryService, OrderRepository orderRepository) {
        this.orderQueryService = orderQueryService;
        this.orderRepository = orderRepository;
    }

    @Override
    public FetchOrderOutput execute(Long orderId) throws IOException {
        Order order = orderRepository.findById(new OrderId(orderId));


        // 注文IDを使用して、注文を検索する
        List<OrderDetailsResult> results = orderQueryService.findOrderDetailsById(orderId);

        // 結果が空の場合は、例外を投げるか、または適切な処理を行う
        if (results.isEmpty()) {
            throw new RuntimeException("Order not found");
        }

        // 最初のOrderDetailsResultを取得（全てのアイテムで共通のフィールドを持っていると仮定）
        OrderDetailsResult firstResult = results.get(0);

        // Stream APIを使用してOrderItemOutputリストを作成
        List<FetchOrderOutput.OrderItemOutput> orderItems = results.stream()
                .map(result -> new FetchOrderOutput.OrderItemOutput(
                        result.productName(),
                        result.productPrice(),
                        result.quantity(),
                        result.subTotalAmount()))
                .collect(Collectors.toList());

        // FetchOrderOutputオブジェクトを作成して返す
        return new FetchOrderOutput(
                firstResult.id(),
                firstResult.orderDate(),
                firstResult.totalAmount(),
                orderItems
        );
    }

}
