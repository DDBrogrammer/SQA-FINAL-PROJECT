package com.my_project.niit_final_project.repositories;

import com.my_project.niit_final_project.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
