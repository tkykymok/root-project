package com.example.mainservice.domain.service;

import com.example.mainservice.domain.model.valueobject.ProductId;
import com.example.mainservice.domain.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public boolean isProductValid(ProductId productId) {
        return productRepository.existsById(productId);
    }
}

