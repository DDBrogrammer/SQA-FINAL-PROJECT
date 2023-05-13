package com.my_project.niit_final_project.controllers.admin;

import com.my_project.niit_final_project.entities.*;
import com.my_project.niit_final_project.services.OrderProductService;
import com.my_project.niit_final_project.services.OrderService;
import com.my_project.niit_final_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collection;

@Controller
@RequestMapping("/admin/order")
public class OrderController implements ICRUD<Order> {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Override
    public String add(Model model) {
        return null;
    }

    @Override
    public String doAdd(Order order, RedirectAttributes flashSession) {
        return null;
    }

    @Override
    public String doAddWithImage(Order order, RedirectAttributes flashSession, MultipartFile multipartFile) {
        return null;
    }
    @Override
    @GetMapping("/list")
    public String list(Model model, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "activePage", defaultValue = "0") int activePage) {
        int totalPage = orderService.getPageOrder(activePage,10).getTotalPages();
        if (page < 0 || page > totalPage - 1) {
            Page<Order> listProductPage = orderService.getPageOrder(activePage,10);
            model.addAttribute("listOrderPage", listProductPage);
            model.addAttribute("activePage", activePage);
        } else {
            Page<Order> listProductPage = orderService.getPageOrder(page,10);
            model.addAttribute("listOrderPage", listProductPage);
            model.addAttribute("activePage", page);
        }

        return "admin/order/list";
    }

    @Override
    public String delete(long id, RedirectAttributes flashSession) {
        return null;
    }

    @Override
    public String edit(Model model, long id) {
        return null;
    }

    @Override
    @PostMapping("/do-edit")
    public String doEdit(Order order, RedirectAttributes flashSession) {
         Order newOrder= orderService.getOrderById(order.getId());
         newOrder.setStatus(order.getStatus());
        orderService.save(newOrder);
        return "redirect:/admin/order/list";
    }

    @Override

    public String doEditWithImage(Order order, RedirectAttributes flashSession, MultipartFile multipartFile) {
        return null;
    }



    @GetMapping("/show_detail")
    public String showDetail(Model model,@RequestParam(name ="id") long id) {
        Order order = orderService.getOrderById(id);
        User user= userService.getUserById(order.getUserId());
        Collection<OrderProduct> orderProducts=order.getOrderProducts();
         double subtotal=order.getTotal()/1.1;
        double tax=subtotal*0.1;
        double total=order.getTotal();
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_DOWN);
        model.addAttribute("order", order);
        model.addAttribute("subtotal",df.format(subtotal));
        model.addAttribute("tax",df.format(tax));
        model.addAttribute("total",df.format(total));
        model.addAttribute("user", user);
        model.addAttribute("orderProducts",orderProducts);
        return "admin/order/detail";
    }
}
