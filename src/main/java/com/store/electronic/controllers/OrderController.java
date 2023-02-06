package com.store.electronic.controllers;

import com.store.electronic.dtos.OrderDto;
import com.store.electronic.services.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}/create/{cartId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto, @PathVariable String userId, @PathVariable String cartId) {
        OrderDto order = orderService.createOrder(orderDto, userId, cartId);
        logger.info("Order created: {}", order.getOrderId());
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> removeOrder(@PathVariable String orderId) {
        orderService.removeOrder(orderId);
        logger.info("Order successfully removed");
        return new ResponseEntity<>("Order Removed!!!", HttpStatus.OK);
    }

    @GetMapping("/get/{userId}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(@PathVariable String userId) {
        List<OrderDto> orderByUserId = orderService.getOrderByUserId(userId);
        logger.info("No. of Orders by user: {}", orderByUserId.size());
        return new ResponseEntity<>(orderByUserId, HttpStatus.OK);
    }

    @GetMapping("/allOrders")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<OrderDto>> getAllOrders(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "orderId", required = false) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        List<OrderDto> allOrders = orderService.getAllOrders(pageNumber, pageSize, sortBy, sortOrder);
        logger.info("No. of Orders: {}", allOrders.size());
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    @PutMapping("/update/{orderId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto orderDto, @PathVariable String orderId) {
        OrderDto order = orderService.updateOrder(orderDto, orderId);
        logger.info("Order updated: {}", order.getOrderId());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
