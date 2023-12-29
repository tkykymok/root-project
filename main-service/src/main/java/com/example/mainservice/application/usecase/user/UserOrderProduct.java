package com.example.mainservice.application.usecase.user;

public class UserOrderProduct {
    public static final String COLUMN_USER_ID = "userId";
    public static final String COLUMN_USER_NAME = "userName";
    public static final String COLUMN_USER_EMAIL = "userEmail";
    public static final String COLUMN_ORDER_ID = "orderId";
    public static final String COLUMN_ORDER_DATE = "orderDate";
    public static final String COLUMN_PRODUCT_ID = "productId";
    public static final String COLUMN_PRODUCT_NAME = "productName";
    public static final String COLUMN_PRODUCT_PRICE = "productPrice";

    private Long userId;
    private String userName;
    private String userEmail;
    private Long orderId;
    private String orderDate;
    private Long productId;
    private String productName;
    private Double productPrice;

    public UserOrderProduct() {
    }

    // コンストラクタ、ゲッター、セッターなど
    public UserOrderProduct(Long userId, String userName, String userEmail, Long orderId, String orderDate, Long productId, String productName, Double productPrice) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }
}
