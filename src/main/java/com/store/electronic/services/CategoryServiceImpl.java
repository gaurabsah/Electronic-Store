package com.store.electronic.services;

import com.store.electronic.dtos.CategoryDto;
import com.store.electronic.entities.Category;
import com.store.electronic.exceptions.ResourceNotFoundException;
import com.store.electronic.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${category.profile.image.path}")
    private String imagePath;

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        //        dto -> entity
        Category category = dtoToEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);

//        entity -> dto
        CategoryDto savedCategoryDto = entityToDto(savedCategory);
        logger.info("Saved Category: {}", savedCategoryDto);
        return savedCategoryDto;
    }

    private CategoryDto entityToDto(Category savedCategory) {
        return mapper.map(savedCategory, CategoryDto.class);
    }

    private Category dtoToEntity(CategoryDto categoryDto) {
        return mapper.map(categoryDto, Category.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        String imageFullPath = imagePath + category.getCoverImage();
        try {
            Path path = Paths.get(imageFullPath);
            Files.delete(path);
            logger.info("File deleted: {}", imageFullPath);
        } catch (Exception e) {
            logger.info("No Such File Found");
            e.printStackTrace();
        }
        categoryRepository.delete(category);
        logger.info("Category deleted: {}", category);
    }

    @Override
    public CategoryDto getCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        CategoryDto categoryDto = entityToDto(category);
        logger.info("Category found: {}", categoryDto);
        return categoryDto;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category savedCategory = categoryRepository.save(category);
        CategoryDto savedCategoryDto = entityToDto(savedCategory);
        logger.info("Category updated: {}", savedCategoryDto);
        return savedCategoryDto;
    }

    @Override
    public List<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = (sortOrder.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        List<Category> categories = page.getContent();
        List<CategoryDto> categoryDtos = categories.stream().map(category -> entityToDto(category)).collect(Collectors.toList());
        logger.info("Categories found: {}", categoryDtos);
        return categoryDtos;
    }
}
