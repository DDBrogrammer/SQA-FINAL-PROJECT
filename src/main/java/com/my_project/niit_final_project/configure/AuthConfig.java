package com.my_project.niit_final_project.configure;


import com.my_project.niit_final_project.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService customerDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
                .and().authorizeRequests().antMatchers("*/checkout").hasAnyRole("ADMIN","USER")
                .and().authorizeRequests().antMatchers("/","/client/**","/client/cart/**","/css/*", "/js/*").permitAll()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/client/home/").and().logout().logoutUrl("/logout")
                .and().exceptionHandling().accessDeniedPage("/access_deny");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // config ok
        auth.inMemoryAuthentication().withUser("admin@gmail.com").password(passwordEncoder.encode("admin")).roles("ADMIN");
        auth.userDetailsService(this.customerDetailsService).passwordEncoder(passwordEncoder());
    }
}
