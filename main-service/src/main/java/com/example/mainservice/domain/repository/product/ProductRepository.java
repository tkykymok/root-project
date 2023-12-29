package com.example.mainservice.domain.repository.product;

import com.example.mainservice.domain.model.order.Product;
import com.example.mainservice.domain.model.valueobject.ProductId;

import java.util.List;

public interface ProductRepository {

    List<Product> findAllById(List<ProductId> productIds);

    boolean existsById(ProductId productId);
}
