package com.bigring.zerohanjpa;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bigring.zerohanjpa.domain.Address;
import com.bigring.zerohanjpa.domain.Delivery;
import com.bigring.zerohanjpa.domain.Member;
import com.bigring.zerohanjpa.domain.Order;
import com.bigring.zerohanjpa.domain.OrderItem;
import com.bigring.zerohanjpa.domain.item.Book;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            var member = createMember("userA", "서울", "1", "1111");
            em.persist(member);
            var book1 = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);
            var book2 = createBook("JPA2 BOOK", 20000, 100);
            em.persist(book2);
            var orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            var orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);
            var order = Order.createOrder(member, createDelivery(member),
                orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {

            var member = createMember("userB", "진주", "2", "2222");
            em.persist(member);
            var book1 = createBook("SPRING1 BOOK", 20000, 200);
            em.persist(book1);
            var book2 = createBook("SPRING2 BOOK", 40000, 300);
            em.persist(book2);
            var delivery = createDelivery(member);
            var orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            var orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);
            var order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Member createMember(String name, String city, String street,
            String zipcode) {
            var member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            var book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

        private Delivery createDelivery(Member member) {
            var delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }
}