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
        initService.dbInit2();

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = getMember("userA", "대전", "대덕구", "123456");
            em.persist(member);

            Book bookA = createBook("JPA1 BOOK", 10000, 100);
            em.persist(bookA);

            Book bookB = createBook("JPA2 BOOK", 20000, 100);
            em.persist(bookB);

            OrderItem orderItemA = OrderItem.createOrderItem(bookA, 10000, 1);
            OrderItem orderItemB = OrderItem.createOrderItem(bookB, 20000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItemA, orderItemB);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = getMember("userB", "서울", "강남구", "789101");
            em.persist(member);

            Book bookA = createBook("SPRING1 BOOK", 20000, 200);
            em.persist(bookA);

            Book bookB = createBook("SPRING2 BOOK", 40000, 300);
            em.persist(bookB);

            OrderItem orderItemA = OrderItem.createOrderItem(bookA, 20000, 3);
            OrderItem orderItemB = OrderItem.createOrderItem(bookB, 40000, 4);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItemA, orderItemB);
            em.persist(order);
        }

        private Member getMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book bookA = new Book();
            bookA.setName(name);
            bookA.setPrice(price);
            bookA.setStockQuantity(stockQuantity);
            return bookA;
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }
}