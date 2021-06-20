package com.bigring.zerohanjpa.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bigring.zerohanjpa.domain.Address;
import com.bigring.zerohanjpa.domain.Order;
import com.bigring.zerohanjpa.domain.OrderItem;
import com.bigring.zerohanjpa.domain.OrderStatus;
import com.bigring.zerohanjpa.repository.OrderRepository;
import com.bigring.zerohanjpa.repository.OrderSearch;
import com.bigring.zerohanjpa.repository.order.query.OrderFlatDto;
import com.bigring.zerohanjpa.repository.order.query.OrderQueryDto;
import com.bigring.zerohanjpa.repository.order.query.OrderQueryRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.forEach(o -> o.getItem().getName());
        }
        return all;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        return orders.stream()
            .map(OrderDto::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();

        return orders.stream()
            .map(OrderDto::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(
        @RequestParam(value = "offset", defaultValue = "0") int offset,
        @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

        return orders.stream()
            .map(OrderDto::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findAllByDto_optimization();
    }

    // 쿼리는 한 번 날리지만 페이징은 불가능
    @GetMapping("/api/v6/orders")
    public List<OrderFlatDto> ordersV6() {
        // OrderQueryDto 를 반환하려면 메모리에서 루프를 돌리는 수 밖에 없다.
        return orderQueryRepository.findAllByDto_flat();
    }

    @Getter
    static class OrderDto {

        private final Long orderId;
        private final String name;
        private final LocalDateTime orderDate;
        private final OrderStatus orderStatus;
        private final Address address;
        private final List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            order.getOrderItems().forEach(o -> o.getItem().getName());
            orderItems = order.getOrderItems().stream()
                .map(OrderItemDto::new)
                .collect(Collectors.toList());
        }
    }

    @Getter
    static class OrderItemDto {

        private final String itemName;
        private final int orderPrice;
        private final int count;

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}
