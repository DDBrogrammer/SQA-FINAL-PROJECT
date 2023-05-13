package com.my_project.niit_final_project.repositories;

import com.my_project.niit_final_project.entities.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductType,Long> {
}
