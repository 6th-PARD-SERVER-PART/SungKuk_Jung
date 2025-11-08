package com.pard.server.seminar6.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductRequest {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class ProductInfo {
        private Long id;
        private String productName;
        private String color;
        private Integer price;
        private Integer quantity;
        private Boolean available;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class NewProductInfo {
        private String productName;
        private String color;
        private Integer price;
        private Integer quantity;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class ProductQuantityChange {
        private Integer quantity;
    }
}
