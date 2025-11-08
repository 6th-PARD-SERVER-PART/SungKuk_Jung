package com.pard.server.seminar6.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductResponse {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class ColorQuantitySearch {
        private String color;
        private Integer quantity;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class ProductDetailView {
        private String productName;
        private String color;
        private Integer price;
        private Integer quantity;
    }

}
