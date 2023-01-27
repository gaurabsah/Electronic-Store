package com.store.electronic.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderDto {

    private String orderId;

    //    PENDING,DISPATCHED,DELIVERED
    private String orderStatus = "PENDING";

    //    NOT-PAID,PAID
    private String paymentStatus = "NOTPAID";

    private double orderAmount;

    @NotBlank(message = "Billing Address cannot be blank")
    private String billingAddress;

    @NotBlank(message = "Billing Phone cannot be blank")
    private String billingPhone;

    @NotBlank(message = "Billing Name cannot be blank")
    private String billingName;

    private Date orderedDate = new Date();

    private Date deliveryDate;

//    private UserDto user;

    private List<OrderItemDto> orderItems = new ArrayList<>();
}
