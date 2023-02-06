package com.store.electronic.dtos;

import jakarta.validation.constraints.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;

    @NotBlank(message = "Category title cannot be blank")
    @Size(min = 3, max = 100, message = "Category title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Category description cannot be blank")
    @Size(min = 4, max = 1000, message = "Category description must be between 4 and 1000 characters")
    private String description;

    private String coverImage;
}
