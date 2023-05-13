package com.my_project.niit_final_project.services;

import com.my_project.niit_final_project.entities.User;
import com.my_project.niit_final_project.entities.UserRole;
import com.my_project.niit_final_project.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    @Autowired
    UserRoleRepository userRoleRepository;

    public Iterable<UserRole> getAll(){
        return userRoleRepository.findAll() ;
    }

    public UserRole getUserRoleById(long id) {
        System.out.println("id"+id);
        return userRoleRepository.findById(id).get();
    }


}
