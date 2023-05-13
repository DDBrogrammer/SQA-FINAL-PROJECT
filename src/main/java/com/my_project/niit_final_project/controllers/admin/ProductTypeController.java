package com.my_project.niit_final_project.controllers.admin;

import com.my_project.niit_final_project.entities.ProductType;

import com.my_project.niit_final_project.services.ProductTypeService;
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
@Controller
@RequestMapping("/admin/type" )
public class ProductTypeController implements ICRUD<ProductType>{
    @Autowired
    private ProductTypeService productTypeService;
    @Override
    @GetMapping("/add")
    public String add(Model model) {
        ProductType productType = new ProductType();
        model.addAttribute("type", productType);
        return "admin/type/add";
    }
    @PostMapping("/do-add")
    public String doAdd(ProductType productType, RedirectAttributes flashSession) {
        if (productTypeService.save(productType)) {
            flashSession.addFlashAttribute("success", "Success!");
        } else {
            flashSession.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/type/add";
    }

    @Override
    public String doAddWithImage(ProductType user, RedirectAttributes flashSession, MultipartFile multipartFile) {
        return null;
    }

    @Override
    @GetMapping("/list")
    public String list(Model model, @RequestParam(name="page", defaultValue = "0") int page, @RequestParam(name="activePage", defaultValue = "0") int activePage) {
        int totalPage= productTypeService.getPageProductType(activePage,10).getTotalPages();
        if( page<0 || page>totalPage-1 ){
            Page<ProductType> listProductTypePage = productTypeService.getPageProductType(activePage,10);
            model.addAttribute("listProductTypePage",listProductTypePage);
            model.addAttribute("activePage", activePage);
        }else {
            Page<ProductType> listProductTypePage = productTypeService.getPageProductType(page,10);
            model.addAttribute("listProductTypePage",listProductTypePage);
            model.addAttribute("activePage", page);
        }
        return "admin/type/list";
    }

    @Override
    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id") long id, RedirectAttributes flashSession){
        if (productTypeService.delete(id)) {
            flashSession.addFlashAttribute("success", "Success!");
        } else {
            flashSession.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/type/list";
    }

    @Override
    @GetMapping ("/edit")
    public String edit(Model model, @RequestParam(name = "id") long id) {
        ProductType productType= productTypeService.getProductTypeById(id);
        model.addAttribute("productType",productType);
        return "admin/type/edit";
    }

    @Override
    @PostMapping ("/do-edit")
    public String doEdit(ProductType user, RedirectAttributes flashSession) {
        if (productTypeService.save(user)) {
            flashSession.addFlashAttribute("success", "Success!");
        } else {
            flashSession.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/type/list";
    }

    @Override
    public String doEditWithImage(ProductType user, RedirectAttributes flashSession, MultipartFile multipartFile) {
        return null;
    }


}

