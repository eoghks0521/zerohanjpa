package com.bigring.zerohanjpa.repository;

import com.bigring.zerohanjpa.domain.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
