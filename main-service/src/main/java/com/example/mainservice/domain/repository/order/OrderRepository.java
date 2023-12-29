package com.example.mainservice.domain.repository.order;

import com.example.mainservice.domain.model.order.Order;
import com.example.mainservice.domain.model.valueobject.OrderId;
import com.example.mainservice.domain.model.valueobject.VersionKey;

public interface OrderRepository {
    Order findById(OrderId id);

    Order findByIdAndVersion(OrderId id, VersionKey version);

    void insert(Order order);

    void update(Order order);
}
