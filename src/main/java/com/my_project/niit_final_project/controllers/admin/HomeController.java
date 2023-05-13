package com.my_project.niit_final_project.controllers.admin;


import com.my_project.niit_final_project.entities.User;
import com.my_project.niit_final_project.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/home")
public class HomeController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductTypeService productTypeService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;

    @Autowired
    VoucherService voucherService;

       @GetMapping("/")
    public String getAdminHome(Model model){
           Long totalUser= userService.countTotal();
           Long totalOrder=orderService.countTotal();
           Long totalProductType=productTypeService.countTotal();
           Long totalProduct=productService.countTotal();
           Long totalCategory=categoryService.countTotal();
           Long totalVoucher=productTypeService.countTotal();
           model.addAttribute("totalUser",totalUser);
           model.addAttribute("totalOrder",totalOrder);
           model.addAttribute("totalProductType",totalProductType);
           model.addAttribute("totalProduct",totalProduct);
           model.addAttribute("totalCategory",totalCategory);
           model.addAttribute("totalVoucher",totalVoucher);

           return "/admin/home";
       }
}
