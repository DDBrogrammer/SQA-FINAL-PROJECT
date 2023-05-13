package com.my_project.niit_final_project.entities;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name",length = 255)
    String name;

    @Column(name = "description",length = 10000, nullable = true)
    String description;

    @Column(name = "quantity")
    Integer quantity;

    Double finalPrice;

    @Column(name = "price")
    Double price;

    @Column(name = "status")
    Integer status;

    @Column(name = "picture",length = 1000)
    String picture;

    @Column(name = "category_id")
    Long categoryId;

    @ManyToOne()
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    Category category;

    @Column(name = "voucher_id")
    Long  voucherId;

    @ManyToOne()
    @JoinColumn(name = "voucher_id", insertable = false, updatable = false)
    Voucher voucher;
    @Column(name ="created_date",nullable = false)
    LocalDateTime createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Product(Long id, String name, String description, Integer quantity, Double finalPrice, Double price,
        Integer status, String picture, Long categoryId, Category category, Long voucherId,
                   Voucher voucher, LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.finalPrice = finalPrice;
        this.price = price;
        this.status = status;
        this.picture = picture;
        this.categoryId = categoryId;
        this.category = category;
        this.voucherId = voucherId;
        this.voucher = voucher;
        this.createdDate = createdDate;
    }

    public Product() {
    }
}
