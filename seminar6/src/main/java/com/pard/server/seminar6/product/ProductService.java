package com.pard.server.seminar6.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // 새 상품 등록
    public Long save(ProductRequest.NewProductInfo productRequest) {
        Product product = Product.addProduct(
                productRequest.getProductName(),
                productRequest.getColor(),
                productRequest.getPrice(),
                productRequest.getQuantity()
        );

        productRepository.save(product);
        return product.getId();
    }

    // 전체 상품 목록 조회 (홈 화면)
    public List<ProductRequest.ProductInfo> findAll(){
        List<Product> products = productRepository.findAllByOrderByIdDesc();
        return products.stream().map(product ->
                ProductRequest.ProductInfo.builder()
                        .id(product.getId())
                        .productName(product.getProductName())
                        .color(product.getColor())
                        .price(product.getPrice())
                        .quantity(product.getQuantity())
                        .available(product.getAvailable())
                        .build()
        ).toList();
    }

    // 상품 색갈별 수량 변결
    @Transactional
    public List<Long> updateByColor(String color, ProductRequest.ProductQuantityChange productUpdateRequest) {
        List<Product> products = productRepository.findAllByColor(color);
        if (products.isEmpty()) {
            throw new RuntimeException("검색한 색상이 존재하지 않습니다: " + color);
        }
        for (Product p : products) {
            p.updateQuantity(productUpdateRequest);
        }
        return products.stream().map(Product::getId).toList();
    }

    // 상품 상세 정보
    public ProductResponse.ProductDetailView searchProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException((id + " ID를 가진 상품이 존재하지 않습니다.")));
        return ProductResponse.ProductDetailView.builder()
                .productName(product.getProductName())
                .color(product.getColor())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

    // 상품 생상별 상품 수량 조회
    public List<ProductResponse.ColorQuantitySearch> searchColor(String color) {
        List<Product> products = productRepository.findAllByColor(color);
        if (products.isEmpty()) {
            throw new RuntimeException(color + " 존재하지 않습니다.");
        }
        return products.stream()
                .map(p -> ProductResponse.ColorQuantitySearch.builder()
                        .color(p.getColor())
                        .quantity(p.getQuantity())
                        .build())
                .toList();
    }
}
