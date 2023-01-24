package com.store.electronic.dtos;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductDto {

    private String productId;

    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private String productName;

    @NotBlank(message = "Product description cannot be blank")
    @Size(min = 3, max = 1000, message = "Product description must be between 3 and 1000 characters")
    private String productDescription;

//    @Size(message = "Product prize must be greater than 1", min = 1)
    private double productPrice;

    private double discountedPrice;

//    @Size(min = 3, max = 100, message = "Product Quantity must be between 3 and 100 characters")
    @NotNull(message = "Product Quantity cannot be blank")
    private int productQuantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addedDate;

    @NotNull(message = "Product active cannot be blank")
    private boolean active;

    @NotNull(message = "Product stock cannot be blank")
    private boolean stock;
}
