package com.bigring.zerohanjpa.repository.order.simplequery;

import java.time.LocalDateTime;

import com.bigring.zerohanjpa.domain.Address;
import com.bigring.zerohanjpa.domain.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderSimpleQueryDto {

    private final Long orderId;
    private final String name;
    private final LocalDateTime orderDate;
    private final OrderStatus orderStatus;
    private final Address address;
}
