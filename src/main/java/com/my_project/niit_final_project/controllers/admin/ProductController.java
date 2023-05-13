package com.my_project.niit_final_project.controllers.admin;

import com.my_project.niit_final_project.entities.Category;
import com.my_project.niit_final_project.entities.Product;

import com.my_project.niit_final_project.entities.Voucher;
import com.my_project.niit_final_project.services.CategoryService;
import com.my_project.niit_final_project.services.ProductService;
import com.my_project.niit_final_project.services.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/admin/product")
public class ProductController implements ICRUD<Product> {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private VoucherService voucherService;

    @Override
    @GetMapping("/add")
    public String add(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("vouchers",voucherService.getAll());
        return "admin/product/add";
    }

    @Override
    public String doAdd(Product obj, RedirectAttributes flashSession) {
        return null;
    }

    @Override
    @PostMapping("/do-add")
    public String doAddWithImage(Product product, RedirectAttributes flashSession, @RequestParam(name = "img") MultipartFile multipartFile) {
        if (productService.save(product, multipartFile)) {
            flashSession.addFlashAttribute("success", "Add successfully");
        } else {
            flashSession.addFlashAttribute("failed", "Add failed");
        }
        return "redirect:/admin/product/add";
    }

    @Override
    @GetMapping("/list")
    public String list(Model model, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "activePage", defaultValue = "0") int activePage) {
        int totalPage = productService.getPageProduct(activePage,10).getTotalPages();
        Iterable<Category> categoryList = categoryService.getAll();
        List<Voucher> voucherList=voucherService.getAll();
        if (page < 0 || page > totalPage - 1) {
            Page<Product> listProductPage = productService.getPageProduct(activePage,10);
            model.addAttribute("listProductPage", listProductPage);
            model.addAttribute("activePage", activePage);
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("voucherList",voucherList);
        } else {
            Page<Product> listProductPage = productService.getPageProduct(page,10);
            model.addAttribute("listProductPage", listProductPage);
            model.addAttribute("activePage", page);
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("voucherList",voucherList);
        }
        return "admin/product/list";
    }

    @Override
    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id") long id, RedirectAttributes flashSession) {
        if (productService.delete(id)) {
            flashSession.addFlashAttribute("success", "Success!");
        } else {
            flashSession.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/product/list";
    }

    @Override
    @GetMapping("/edit")
    public String edit(Model model, long id) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("vouchers",voucherService.getAll());
        return "admin/product/edit";
    }

    @Override
    public String doEdit(Product product, RedirectAttributes flashSession) {
        return null;
    }

    @Override
    @PostMapping("/do-edit")
    public String doEditWithImage(Product product, RedirectAttributes flashSession, @RequestParam(name = "img") MultipartFile multipartFile) {
        if (productService.save(product, multipartFile)) {
            flashSession.addFlashAttribute("success", "Add successfully");
        } else {
            flashSession.addFlashAttribute("failed", "Add failed");
        }
        return "redirect:/admin/product/list";
    }
}

