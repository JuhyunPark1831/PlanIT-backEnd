package com.sideProject.PlanIT.domain.product.service;

import com.sideProject.PlanIT.common.response.CustomException;
import com.sideProject.PlanIT.common.response.ErrorCode;
import com.sideProject.PlanIT.domain.product.controller.enums.ProductSearchOption;
import com.sideProject.PlanIT.domain.product.dto.request.ProductRequestDto;
import com.sideProject.PlanIT.domain.product.dto.response.ProductResponseDto;
import com.sideProject.PlanIT.domain.product.entity.enums.ProductSellingType;
import com.sideProject.PlanIT.domain.product.entity.Product;
import com.sideProject.PlanIT.domain.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(ProductRequestDto productRequestDto) {
        return productRepository.save(Product.builder()
                        .name(productRequestDto.getName())
                        .period(productRequestDto.getPeriod())
                        .number(productRequestDto.getNumber())
                        .price(productRequestDto.getPrice())
                        .type(productRequestDto.getType())
                        .sellingType(ProductSellingType.SELLING)
                .build());
    }

    @Override
    public String deleteProduct(Long product_id) {
        productRepository.deleteById(product_id);
        return "삭제 성공";
    }

    @Override
    public Page<ProductResponseDto> findAllProducts(ProductSearchOption option, Pageable pageable) {
        Page<Product> products = null;
        if(option == ProductSearchOption.ALL) {
            products = productRepository.findAll(pageable);
        } else if (option == ProductSearchOption.SELLING) {
            products = productRepository.findAllBySellingType(ProductSellingType.SELLING,pageable);
        } else if (option == ProductSearchOption.STOP_SELLING) {
            products = productRepository.findAllBySellingType(ProductSellingType.STOP_SELLING,pageable);
        }

        if(products == null) {
            throw new CustomException("상품이 존재하지 않습니다.", ErrorCode.PRODUCT_NOT_FOUND);
        }

        return products.map(ProductResponseDto::of);
    }

    @Override
    public ProductResponseDto findProduct(Long product_id) {
        return Product.toDto(productRepository.findById(product_id).orElseThrow(() -> new IllegalArgumentException("no exist id")));
    }
}
