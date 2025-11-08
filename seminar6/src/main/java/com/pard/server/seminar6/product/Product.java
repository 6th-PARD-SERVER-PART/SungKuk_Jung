package com.pard.server.seminar6.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String productName;

    @Column(name = "COLOR", nullable = false)
    private String color;

    @Column(name = "PRICE", nullable = false)
    private Integer price;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "AVAILABLE")
    private Boolean available;

    public void updateQuantity(ProductRequest.ProductQuantityChange request) {
        this.quantity = request.getQuantity();
        this.available = this.quantity != null && this.quantity > 0;
    }

    public static Product addProduct(String productName, String color, Integer price, Integer quantity) {
        return Product.builder()
                .productName(productName)
                .color(color)
                .price(price)
                .quantity(quantity)
                .available(quantity != null && quantity > 0)
                .build();
    }
}
