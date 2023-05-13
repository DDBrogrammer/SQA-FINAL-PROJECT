package com.my_project.niit_final_project.entities;



import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "order_products")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "quantity")
    Integer quantity;


    @Column(name = "price")
    Double price;

    @Column(name = "order_id")
    Long orderId;

    @Column(name = "product_id")
    Long productId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", insertable = false,updatable = false)
    public Order order;
}
