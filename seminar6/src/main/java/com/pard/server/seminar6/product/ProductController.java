package com.pard.server.seminar6.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/home")
    public List<ProductRequest.ProductInfo> findAll() {
        return productService.findAll();
    }

    @PostMapping("")
    public Long save(@RequestBody ProductRequest.NewProductInfo product) {
        return productService.save(product);
    }

    @PatchMapping("/{id}")
    public Long updateByColor(@PathVariable Long id, @RequestBody ProductRequest.ProductQuantityChange productUpdateRequest) {
        return productService.updateByColor(id, productUpdateRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            ProductResponse.ProductDetailView product = productService.searchProduct(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<?> findByColor(@PathVariable String color) {
        try {
            List<ProductResponse.ColorQuantitySearch> products = productService.searchColor(color);
            return ResponseEntity.ok(products);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

}
