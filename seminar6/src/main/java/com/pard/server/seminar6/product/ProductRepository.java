package com.pard.server.seminar6.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByIdDesc();
    Optional<Product> findByColor(String color);
    List<Product> findAllByColor(String color);
}
