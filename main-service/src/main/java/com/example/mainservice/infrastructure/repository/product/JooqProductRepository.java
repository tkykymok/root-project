package com.example.mainservice.infrastructure.repository.product;

import com.example.mainservice.domain.model.order.Product;
import com.example.mainservice.domain.model.valueobject.Price;
import com.example.mainservice.domain.model.valueobject.ProductId;
import com.example.mainservice.domain.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.mainservice.infrastructure.jooq.Tables.PRODUCTS;

@Repository
@RequiredArgsConstructor
public class JooqProductRepository implements ProductRepository {

    private final DSLContext dsl;


    @Override
    public List<Product> findAllById(List<ProductId> productIds) {
        // ProductIdの値のリストを取得
        List<Long> ids = productIds.stream()
                .map(ProductId::value)
                .collect(Collectors.toList());

        // IDリストに基づいてデータベースからProductを検索
        Result<Record> records = dsl.select()
                .from(PRODUCTS)
                .where(PRODUCTS.ID.in(ids))
                .fetch();

        // 結果のRecordをProductオブジェクトに変換
        return records.stream()
                .map(this::recordToProduct)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(ProductId productId) {
        return dsl.fetchExists(
                dsl.selectFrom(PRODUCTS)
                        .where(PRODUCTS.ID.eq(productId.value()))
        );
    }

    private Product recordToProduct(Record record) {
        ProductId id = new ProductId(record.get(PRODUCTS.ID));
        String name = record.get(PRODUCTS.NAME);
        Price price = new Price(record.get(PRODUCTS.PRICE));

        return Product.reconstruct(id, name, price);
    }
}
