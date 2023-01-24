package com.store.electronic.services;

import com.store.electronic.dtos.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto, String productId);

    void deleteProduct(String productId);

    ProductDto getProduct(String productId);

    List<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortOrder);

    List<ProductDto> getProductByProductName(String productName, int pageNumber, int pageSize, String sortBy, String sortOrder);

    List<ProductDto> getAllProductsByActive(int pageNumber, int pageSize, String sortBy, String sortOrder);

    //    create product with category
    ProductDto createProductWithCategory(ProductDto productDto, String categoryId);

    //    update product with category
    ProductDto updateProductWithCategory(String productId, String categoryId);

//    get all products using category id
    List<ProductDto> getProductsByCategoryId(String categoryId, int pageNumber, int pageSize, String sortBy, String sortOrder);

//    List<ProductDto> getProductsByCategoryName(String categoryName, int pageNumber, int pageSize, String sortBy, String sortOrder);
}
