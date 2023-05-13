package com.my_project.niit_final_project.entities;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "total")
    Double total;

    @Column(name = "order_date")
    LocalDateTime orderDate;

    @Column(name = "received_address")
    String receivedAddress;

    @Column(name="user_id")
    Long userId;

    @Column(name = "status")
    Integer status;

    @OneToMany(mappedBy = "order" , fetch = FetchType.LAZY)
    Collection<OrderProduct> orderProducts;



}