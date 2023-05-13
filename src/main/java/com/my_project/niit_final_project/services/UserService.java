package com.my_project.niit_final_project.services;

import com.my_project.niit_final_project.entities.User;
import com.my_project.niit_final_project.helpers.Helper;
import com.my_project.niit_final_project.repositories.UserRepository;
import com.my_project.niit_final_project.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class UserService  {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private Helper helper;
    public boolean save(User user) {
        if(user.getId()!=null){
            User tempUser =userRepository.findById(user.getId()).get();
            tempUser.setUserRoles(user.getUserRoles());
            user=tempUser;
            try {
                userRepository.save(user);
            }catch (Exception e){
                return false;
            }
            return true;
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        LocalDateTime localDate = LocalDateTime.now();
        user.setCreatedDate(localDate);
        try {
            userRepository.save(user);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean delete(long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public User getUserById(long id) {
        User user =userRepository.findById(id).get();
        return userRepository.findById(id).get();
    }

    public User getUseByEmail(String email){
        if(userRepository.findByEmail(email).isPresent()){
            return userRepository.findByEmail(email).get();
        }
        return null;
        }

    public boolean checkUser(User user){
        System.out.println("user: "+user);
        Optional<User> tempUser= userRepository.findByEmail(user.getEmail());
        System.out.println(tempUser.toString());
        return true;
    }
    public Page<User> getPageUser(int page ){
        Pageable pageable=  PageRequest.of(page,10);
        return userRepository.findAll(pageable);
    }
    public  Iterable<User> getAll(){
        return userRepository.findAll();
    }
    public  Long countTotal(){
        return userRepository.count();
    }
}
