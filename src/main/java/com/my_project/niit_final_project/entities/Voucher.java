package com.my_project.niit_final_project.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@Table(name = "vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "sale_percentage")
    @Min(0)
    @Max(99)
    Double salePercentage;
    @Column(name = "status")
    Integer status;

    @OneToMany(mappedBy="voucher",fetch = FetchType.LAZY)
    Collection<Product> products;

    @Column(name ="created_date",nullable = false)
    LocalDateTime createdDate;
}
