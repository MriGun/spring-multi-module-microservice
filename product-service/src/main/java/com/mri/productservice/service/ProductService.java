package com.mri.productservice.service;

import com.mri.productservice.dto.ProductRequest;
import com.mri.productservice.dto.ProductResponse;
import com.mri.productservice.entity.Product;
import com.mri.productservice.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepo productRepo;

    public void createProduct(ProductRequest productRequest) {

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepo.save(product);

        log.info("Product {} is saved", product.getName());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .price(product.getPrice())
                .name(product.getName())
                .build();
    }
}
