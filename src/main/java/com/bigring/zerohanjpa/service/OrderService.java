package com.bigring.zerohanjpa.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bigring.zerohanjpa.domain.Delivery;
import com.bigring.zerohanjpa.domain.Member;
import com.bigring.zerohanjpa.domain.Order;
import com.bigring.zerohanjpa.domain.OrderItem;
import com.bigring.zerohanjpa.domain.item.Item;
import com.bigring.zerohanjpa.repository.ItemRepository;
import com.bigring.zerohanjpa.repository.MemberRepository;
import com.bigring.zerohanjpa.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);

        order.cancel();

    }
}
