package com.my_project.niit_final_project.repositories;

import com.my_project.niit_final_project.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository  extends JpaRepository<UserRole,Long> {
    
}
