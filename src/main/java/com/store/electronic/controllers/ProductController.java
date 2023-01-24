package com.store.electronic.controllers;

import com.store.electronic.dtos.ProductDto;
import com.store.electronic.services.FileService;
import com.store.electronic.services.ProductService;
import com.store.electronic.utils.ImageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.profile.image.path}")
    private String imageUploadPath;

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto product = productService.createProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable String productId) {
        ProductDto updateProduct = productService.updateProduct(productDto, productId);
        logger.info("Update product: {}", updateProduct.toString());
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        logger.info("Delete product: {}", productId);
        return new ResponseEntity<>("Product deleted", HttpStatus.OK);
    }

    @GetMapping("/get/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {
        ProductDto product = productService.getProduct(productId);
        logger.info("Get product: {}", product.getProductId());
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "productName", required = false) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {

        List<ProductDto> products = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
        logger.info("Get all products {}", products.size());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/search/{productName}")
    public ResponseEntity<List<ProductDto>> getProductsByName(
            @PathVariable String productName,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "productName", required = false) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        List<ProductDto> products = productService.getProductByProductName(productName, pageNumber, pageSize, sortBy, sortOrder);
        logger.info("Get products by name {}", products.size());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/search/active")
    public ResponseEntity<List<ProductDto>> getProductsByActive(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "productName", required = false) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        List<ProductDto> products = productService.getAllProductsByActive(pageNumber, pageSize, sortBy, sortOrder);
        logger.info("Get products by active {}", products.size());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/uploadProductImage/{productId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("productImage") MultipartFile image, @PathVariable String productId) throws IOException {
        String imageName = fileService.uploadFile(image, imageUploadPath);

        ProductDto product = productService.getProduct(productId);
        product.setProductImage(imageName);
        ProductDto productDto = productService.updateProduct(product, productId);

        ImageResponse imageResponse = ImageResponse.builder()
                .imageName(imageName)
                .success(true)
                .status(HttpStatus.CREATED)
                .message("Image uploaded successfully!!!")
                .build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getProductImage/{productId}")
    public void downloadUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto product = productService.getProduct(productId);
        logger.info("Product Image Name: {}", product.getProductImage());
        InputStream resource = fileService.downloadFile(imageUploadPath, product.getProductImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        logger.info("Product Image Downloaded Successfully!!!");
    }
}
