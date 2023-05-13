package com.my_project.niit_final_project.services;

import com.my_project.niit_final_project.entities.*;
import com.my_project.niit_final_project.repositories.OrderProductRepository;
import com.my_project.niit_final_project.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    UserService userService;

    public void makeOrder(String shipAddress,  double total, ArrayList<CartProduct> cartProducts){
        Order order=new Order();
        LocalDateTime localDate = LocalDateTime.now();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userService.getUseByEmail(currentPrincipalName);
        order.setReceivedAddress(shipAddress);
        order.setUserId(user.getId());
        order.setTotal(total);
        order.setOrderDate(localDate);
        order.setStatus(1);
        orderRepository.save(order);
        for (CartProduct cartProduct: cartProducts) {
            OrderProduct orderProduct=new OrderProduct();
            orderProduct.setName(cartProduct.getName());
            orderProduct.setPrice(cartProduct.getPrice());
            orderProduct.setQuantity(cartProduct.getQuantity());
            orderProduct.setOrderId(order.getId());
            orderProduct.setProductId(cartProduct.getId());
            orderProductRepository.save(orderProduct);
        }
    }



    public  Long countTotal(){
        return orderRepository.count();
    }
    public boolean save(Order order) {

        LocalDateTime localDate = LocalDateTime.now();
        /*  user.setPassword(helper.getMD5(user.getPassword()));*/
        order.setOrderDate(localDate);

        try {
            orderRepository.save(order);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean delete(long id) {
        try {
            orderRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Order getOrderById(long id) {
        return orderRepository.findById(id).get();
    }

    public Page<Order> getPageOrder(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable);
    }
    public  Iterable<Order> getAll(){

        return orderRepository.findAll();
    }
}
