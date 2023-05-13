package com.my_project.niit_final_project.repositories;

import com.my_project.niit_final_project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
   Optional <User> findByEmail(String email);
}
