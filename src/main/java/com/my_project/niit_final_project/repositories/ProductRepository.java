package com.my_project.niit_final_project.repositories;

import com.my_project.niit_final_project.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product,Long> {
    public Page<Product> findByCategoryId(long id, Pageable pageable);

    @Query("SELECT p FROM Product p where p.name LIKE %:keywords%")
    public Page<Product> searchProducts(String keywords,Pageable pageable);
}
