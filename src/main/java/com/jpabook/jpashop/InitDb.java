package com.jpabook.jpashop;

import com.jpabook.jpashop.domain.*;
import com.jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;


/**
 *  총 주문 2개
 * * userA
 *   * JPA1 BOOK
 *   * JPA2 BOOK
 * * userB
 *   * SPRING1 BOOK
 *   * SPRING2 BOOK
 */
@Component
@RequiredArgsConstructor
public class InitDb {


    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = new Member();
            member.setName("userA");
            member.setAddress(new Address("대전", "대덕구" , "123456"));
            em.persist(member);

            Book bookA = new Book();
            bookA.setName("JPA1 BOOK");
            bookA.setPrice(10000);
            bookA.setStockQuantity(100);
            em.persist(bookA);

            Book bookB = new Book();
            bookB.setName("JPA1 BOOK");
            bookB.setPrice(10000);
            bookB.setStockQuantity(100);
            em.persist(bookB);

            OrderItem orderItemA = OrderItem.createOrderItem(bookA, 10000, 1);
            OrderItem orderItemB = OrderItem.createOrderItem(bookB, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItemA, orderItemB);
            em.persist(order);
        }
    }
}