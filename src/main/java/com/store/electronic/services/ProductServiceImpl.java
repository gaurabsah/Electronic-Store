package com.store.electronic.services;

import com.store.electronic.dtos.ProductDto;
import com.store.electronic.entities.Product;
import com.store.electronic.exceptions.ResourceNotFoundException;
import com.store.electronic.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public ProductDto createProduct(ProductDto productDto) {
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);
        Product product = dtoToEntity(productDto);
        Product savedProduct = productRepository.save(product);
        ProductDto newProductDto = entityToDto(savedProduct);
        logger.info("Product created: {}", newProductDto.getProductId());
        return newProductDto;
    }

    private ProductDto entityToDto(Product savedProduct) {
        return mapper.map(savedProduct, ProductDto.class);
    }

    private Product dtoToEntity(ProductDto productDto) {
        return mapper.map(productDto, Product.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setProductName(productDto.getProductName());
        product.setProductDescription(productDto.getProductDescription());
        product.setProductPrice(productDto.getProductPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setProductQuantity(productDto.getProductQuantity());
        product.setActive(productDto.isActive());
        product.setStock(productDto.isStock());
        Product updatedProduct = productRepository.save(product);
        ProductDto updatedProductDto = entityToDto(updatedProduct);
        logger.info("Product updated: {}", updatedProductDto.getProductId());
        return updatedProductDto;
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
        logger.info("Product deleted: {}", productId);

    }

    @Override
    public ProductDto getProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        ProductDto productDto = entityToDto(product);
        logger.info("Product found: {}", productDto.getProductId());
        return productDto;
    }

    @Override
    public List<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = (sortOrder.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findAll(pageable);
        List<Product> products = page.getContent();
        List<ProductDto> productDtos = products.stream().map(product -> entityToDto(product)).collect(Collectors.toList());
        logger.info("Products found: {}", productDtos.size());
        return productDtos;
    }

    @Override
    public List<ProductDto> getProductByProductName(String productName, int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = (sortOrder.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByProductNameContaining(productName, pageable);
        List<Product> products = page.getContent();
        List<ProductDto> productDtos = products.stream().map(product -> entityToDto(product)).collect(Collectors.toList());
        logger.info("Products found: {}", productDtos.size());
        return productDtos;
    }

    @Override
    public List<ProductDto> getAllProductsByActive(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = (sortOrder.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByActive(true, pageable);
        List<Product> products = page.getContent();
        List<ProductDto> productDtos = products.stream().map(product -> entityToDto(product)).collect(Collectors.toList());
        logger.info("Products found: {}", productDtos.size());
        return productDtos;
    }
}
