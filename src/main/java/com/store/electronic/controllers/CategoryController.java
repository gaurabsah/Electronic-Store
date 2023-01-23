package com.store.electronic.controllers;

import com.store.electronic.dtos.CategoryDto;
import com.store.electronic.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto newCategoryDto = categoryService.saveCategory(categoryDto);
        logger.info("Category created: {}", newCategoryDto);
        return new ResponseEntity<>(newCategoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
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
}
