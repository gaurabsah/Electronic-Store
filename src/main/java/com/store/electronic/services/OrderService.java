package com.store.electronic.services;

import com.store.electronic.dtos.OrderDto;

import java.util.List;

public interface OrderService {

    //    create order
    OrderDto createOrder(OrderDto orderDto, String userId, String cartId);

    //    remove order
    void removeOrder(String orderId);

    //    get order by user
    List<OrderDto> getOrderByUserId(String userId);

    //    get All orders -> Admin use
    List<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortOrder);

    //    update order
    OrderDto updateOrder(OrderDto orderDto, String orderId);
}
