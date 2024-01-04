package com.example.mainservice.domain.service.impl;

import com.example.mainservice.domain.model.valueobject.ProductId;
import com.example.mainservice.domain.repository.product.ProductRepository;
import com.example.mainservice.domain.service.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductValidatorImpl implements ProductValidator {

    private final ProductRepository productRepository;

    /**
     * 商品IDのリストをチェックする
     *
     * @param productIds 商品IDのリスト
     */
    @Override
    public void validateProductIds(List<ProductId> productIds) {
        for (ProductId productId : productIds) {
            if (!isProductValid(productId)) {
                throw new RuntimeException("商品ID: " + productId.value() + " は存在しません");
            }
        }
    }

    /**
     * 商品IDが存在するかチェックする
     *
     * @param productId 商品ID
     * @return 存在する場合は true
     */
    private boolean isProductValid(ProductId productId) {
        return productRepository.existsById(productId);
    }
}

