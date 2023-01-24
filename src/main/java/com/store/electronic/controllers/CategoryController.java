package com.store.electronic.controllers;

import com.store.electronic.dtos.CategoryDto;
import com.store.electronic.dtos.ProductDto;
import com.store.electronic.dtos.UserDto;
import com.store.electronic.services.CategoryService;
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
@RequestMapping("/api/category")
public class CategoryController {

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Value("${category.profile.image.path}")
    private String imageUploadPath;

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto newCategoryDto = categoryService.saveCategory(categoryDto);
        logger.info("Category created: {}", newCategoryDto);
        return new ResponseEntity<>(newCategoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
        CategoryDto updatedCategoryDto = categoryService.updateCategory(categoryDto, categoryId);
        logger.info("Category updated: {}", updatedCategoryDto);
        return new ResponseEntity<>(updatedCategoryDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
        logger.info("Category deleted, CategoryId: {}", categoryId);
        return new ResponseEntity<>("Category deleted...", HttpStatus.OK);
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable String categoryId) {
        CategoryDto categoryDto = categoryService.getCategory(categoryId);
        logger.info("Category found: {}", categoryDto);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDto>> getAllCategories(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "categoryId", required = false) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        List<CategoryDto> categoryDtos = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        logger.info("Categories found: {}", categoryDtos);
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @PostMapping("/uploadCategoryImage/{categoryId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("categoryImage") MultipartFile image, @PathVariable String categoryId) throws IOException {
        String imageName = fileService.uploadFile(image, imageUploadPath);

        CategoryDto category = categoryService.getCategory(categoryId);
        category.setCoverImage(imageName);
        CategoryDto categoryDto = categoryService.updateCategory(category, categoryId);

        ImageResponse imageResponse = ImageResponse.builder()
                .imageName(imageName)
                .success(true)
                .status(HttpStatus.CREATED)
                .message("Image uploaded successfully!!!")
                .build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getCategoryImage/{categoryId}")
    public void downloadUserImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        CategoryDto category = categoryService.getCategory(categoryId);
        logger.info("Category Image Name: {}", category.getCoverImage());
        InputStream resource = fileService.downloadFile(imageUploadPath, category.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        logger.info("Category Image Downloaded Successfully!!!");
    }

//    create product with category

    @PostMapping("/{categoryId}/createProduct")
    public ResponseEntity<ProductDto> createProductWithCategory(@Valid @RequestBody ProductDto productDto, @PathVariable String categoryId) {
        ProductDto newProductDto = productService.createProductWithCategory(productDto, categoryId);
        logger.info("Product created: {}", newProductDto.getProductId());
        return new ResponseEntity<>(newProductDto, HttpStatus.CREATED);
    }

//    update product with category

    @PutMapping("/{categoryId}/updateProduct/{productId}")
    public ResponseEntity<ProductDto> updateProductWithCategory(@PathVariable String productId, @PathVariable String categoryId) {
        ProductDto updatedProductDto = productService.updateProductWithCategory(productId, categoryId);
        return new ResponseEntity<>(updatedProductDto, HttpStatus.OK);
    }

    @GetMapping("/{categoryID}/getProducts")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(
            @PathVariable String categoryID,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "productId", required = false) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        List<ProductDto> productDtos = productService.getProductsByCategoryId(categoryID, pageNumber, pageSize, sortBy, sortOrder);
        logger.info("Products found: {}", productDtos);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
}
