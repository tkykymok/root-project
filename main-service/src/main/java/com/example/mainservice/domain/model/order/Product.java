package com.example.mainservice.domain.model.order;

import com.example.mainservice.domain.model.valueobject.Price;
import com.example.mainservice.domain.model.valueobject.ProductId;
import com.example.shared.domain.model.SingleKeyBaseEntity;
import lombok.Getter;

@Getter
public class Product extends SingleKeyBaseEntity<ProductId> {
    private String name;
    private Price price;

    private Product() {}

    public static Product reconstruct(ProductId id, String name, Price price) {
        Product product = new Product();
        product.id = id;
        product.name = name;
        product.price = price;
        return product;
    }

}
