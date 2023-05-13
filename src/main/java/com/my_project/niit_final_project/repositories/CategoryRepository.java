package com.my_project.niit_final_project.repositories;

import com.my_project.niit_final_project.entities.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
