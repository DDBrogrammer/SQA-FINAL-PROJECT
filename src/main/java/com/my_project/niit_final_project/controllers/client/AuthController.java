package com.my_project.niit_final_project.controllers.client;

import com.my_project.niit_final_project.entities.*;
import com.my_project.niit_final_project.services.OrderService;
import com.my_project.niit_final_project.services.UserRoleService;
import com.my_project.niit_final_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/login")
    public String login(){
        return "/client/auth/login";
    }

    @GetMapping("/logout")
    public  String logout(){
        return "/client/home";
    }
    @GetMapping("/signup")
    public  String signup(Model model){
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("roles",userRoleService.getAll());
        return "/client/auth/signup";
    }
    @PostMapping("/do-signup")
    public  String doSignup(User user){
            Set<UserRole> roles= new HashSet<UserRole>();
            long userID=2;
            roles.add(userRoleService.getUserRoleById(userID)) ;
            user.setUserRoles(roles);
            userService.save(user);
            return "redirect:/login";
    }

    @GetMapping("/checkout")
    public  String checkout( ){

        return "/client/auth/checkout";
    }
    @PostMapping("do-checkout")
    public  String doCheckout(HttpSession session, @RequestParam(name="address") String address){
        double totalPrice =(double) session.getAttribute("TOTAL_PRICE");
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_DOWN);
        totalPrice= Double.parseDouble(df.format(totalPrice));
        ArrayList<CartProduct> cartProducts = (ArrayList<CartProduct>) session.getAttribute("CART");
        orderService.makeOrder(address,totalPrice*1.1,cartProducts);
        ArrayList<CartProduct> newCartProducts=new ArrayList<CartProduct>();
        session.setAttribute("CART",newCartProducts);
        return "redirect:/client/home";
    }

    @GetMapping("/client/get-order")
    @ResponseBody
    public ResponseEntity<?> getCart(HttpSession session){
        if (session.getAttribute("CART")==null){
            return ResponseEntity.ok(new ArrayList<CartProduct>());
        }
        ArrayList<CartProduct> cartProducts = (ArrayList<CartProduct>) session.getAttribute("CART");
        return ResponseEntity.ok(cartProducts);

    }



}
