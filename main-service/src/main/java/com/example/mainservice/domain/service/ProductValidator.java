package com.example.mainservice.domain.service;

import com.example.mainservice.domain.model.valueobject.ProductId;
import com.example.mainservice.domain.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductValidator {
    void validateProductIds(List<ProductId> productIds);
}

