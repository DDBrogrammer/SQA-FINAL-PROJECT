package com.my_project.niit_final_project.controllers.admin;

import com.my_project.niit_final_project.entities.Category;
import com.my_project.niit_final_project.entities.ProductType;
import com.my_project.niit_final_project.services.CategoryService;
import com.my_project.niit_final_project.services.ProductTypeService;
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
@RequestMapping("/admin/category")
public class CategoryController implements ICRUD<Category>{
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductTypeService productTypeService;
    @Override
    @GetMapping("/add")
    public String add(Model model) {
        Category category = new Category();
        Iterable<ProductType> typeList= productTypeService.getAll();
        model.addAttribute("typeList",typeList);
        model.addAttribute("category", category);
        return "admin/category/add";
    }
    @PostMapping("/do-add")
    public String doAdd(Category category, RedirectAttributes flashSession) {
        if (categoryService.save(category)) {
            flashSession.addFlashAttribute("success", "Success!");
        } else {
            flashSession.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/category/add";
    }

    @Override
    public String doAddWithImage(Category category, RedirectAttributes flashSession, MultipartFile multipartFile) {
        return null;
    }

    @Override
    @GetMapping("/list")
    public String list(Model model, @RequestParam(name="page", defaultValue = "0") int page, @RequestParam(name="activePage", defaultValue = "0") int activePage) {
        int totalPage= categoryService.getPageCategory(activePage,20).getTotalPages();
         Iterable<ProductType> typeList =  productTypeService.getAll();

       /* System.out.println("Gui len"+page);
        System.out.println("Tong"+totalPage);
        System.out.println("trang hien tai "+activePage);*/
        if( page<0 || page>totalPage-1 ){
          /*  System.out.println("Gui len sai"+page);
            System.out.println("trang hien tai sai "+activePage);*/
            Page<Category> listCategoryPage = categoryService.getPageCategory(activePage,20);
            model.addAttribute("listCategoryPage",listCategoryPage);
            model.addAttribute("activePage", activePage);
            model.addAttribute("typeList",typeList);


        }else {
           /* System.out.println("Gui len dung"+page);
            System.out.println("trang hien tai dung "+activePage);*/
            Page<Category> listCategoryPage = categoryService.getPageCategory(page,20);
            model.addAttribute("listCategoryPage",listCategoryPage);
            model.addAttribute("activePage", page);
            model.addAttribute("typeList",typeList);

        }

        return "admin/category/list";
    }

    @Override
    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id") long id, RedirectAttributes flashSession){
        if (categoryService.delete(id)) {
            flashSession.addFlashAttribute("success", "Success!");
        } else {
            flashSession.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/category/list";
    }

    @Override
    @GetMapping ("/edit")
    public String edit(Model model, @RequestParam(name = "id") long id) {
        Category category= categoryService.getCategoryById(id);
        Iterable<ProductType> typeList= productTypeService.getAll();

        model.addAttribute("typeList",typeList);
        model.addAttribute("category",category);
        return "admin/category/edit";
    }

    @Override
    @PostMapping ("/do-edit")
    public String doEdit(Category category, RedirectAttributes flashSession) {
        if (categoryService.save(category)) {
            flashSession.addFlashAttribute("success", "Success!");
        } else {
            flashSession.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/category/list";
    }

    @Override
    public String doEditWithImage(Category user, RedirectAttributes flashSession, MultipartFile multipartFile) {
        return null;
    }























}
