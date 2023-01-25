package com.store.electronic.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CartDto {

    private String cartId;

    private Date createdAt;

    private UserDto user;

    private List<CartItemDto> cartItems = new ArrayList<>();
}
