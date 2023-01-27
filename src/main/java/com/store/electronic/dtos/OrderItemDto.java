package com.store.electronic.dtos;

import com.store.electronic.entities.Product;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderItemDto {

    private int orderItemId;

    private int quantity;

    private double totalPrice;

    private ProductDto product;

//    private OrderDto order;
}
