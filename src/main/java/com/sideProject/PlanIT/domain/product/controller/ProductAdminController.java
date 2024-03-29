package com.sideProject.PlanIT.domain.product.controller;

import com.sideProject.PlanIT.common.response.ApiResponse;
import com.sideProject.PlanIT.domain.product.dto.request.ProductRequestDto;
import com.sideProject.PlanIT.domain.product.entity.Product;
import com.sideProject.PlanIT.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductAdminController {
    private final ProductService productService;

    @PostMapping
    public ApiResponse<Product> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return ApiResponse.ok(productService.createProduct(productRequestDto));
    }

    @PutMapping("/{product_id}")
    public ApiResponse<String> stopSelling(@PathVariable Long product_id) {
        return ApiResponse.ok(productService.stopProductSell(product_id));
    }
}
