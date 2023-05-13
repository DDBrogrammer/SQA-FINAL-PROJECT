package com.my_project.niit_final_project.services;

import com.my_project.niit_final_project.repositories.UserRepository;
import com.my_project.niit_final_project.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.my_project.niit_final_project.entities.User;
import com.my_project.niit_final_project.entities.UserRole;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository  userRoleRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).get();
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        Set<UserRole> roles =  user.getUserRoles();
        Set<GrantedAuthority> grantRoles = new HashSet<>();
        for (UserRole role: roles
        ) {
            grantRoles.add(new SimpleGrantedAuthority(role.getName()));//ROLE_STAFF, ROLE_ADMIN
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),grantRoles);
    }
}
