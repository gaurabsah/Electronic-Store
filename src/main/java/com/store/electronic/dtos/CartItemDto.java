package com.store.electronic.dtos;

import com.store.electronic.entities.Cart;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CartItemDto {

    private int cartItemId;

    private ProductDto product;

    private int quantity;

    private double totalPrice;

//    private CartDto cart;
}
