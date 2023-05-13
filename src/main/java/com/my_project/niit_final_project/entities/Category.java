package com.my_project.niit_final_project.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "status")
    Integer status;

    @Column(name = "product_type_id")
    Long typeId;

    @ManyToOne()
    @JoinColumn(name = "product_type_id", insertable = false, updatable = false)
    ProductType productType;

    @OneToMany(mappedBy="category",fetch = FetchType.LAZY)
    Collection<Product> products;
    @Column(name ="created_date",nullable = false)
    LocalDateTime createdDate;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", typeId=" + typeId +
                ", productType=" + productType +
                ", products=" + products +
                ", createdDate=" + createdDate +
                '}';
    }
}
