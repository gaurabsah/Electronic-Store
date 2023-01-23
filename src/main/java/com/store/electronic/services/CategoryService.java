package com.store.electronic.services;

import com.store.electronic.dtos.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto saveCategory(CategoryDto categoryDto);

    void deleteCategory(String categoryId);

    CategoryDto getCategory(String categoryId);

    CategoryDto updateCategory(CategoryDto categoryDto, String categoryId);

    List<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder);
}
