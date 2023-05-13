package com.my_project.niit_final_project.controllers.admin;

import com.my_project.niit_final_project.entities.User;
import com.my_project.niit_final_project.services.CategoryService;
import com.my_project.niit_final_project.services.UserRoleService;
import com.my_project.niit_final_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
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
@RequestMapping("/admin/user" )
public class UserController implements ICRUD<User>{
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Override
    @GetMapping("/add")
    public String add(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("roles",userRoleService.getAll());
        return "admin/user/add";
    }
    @PostMapping("/do-add")
    public String doAdd(User user, RedirectAttributes flashSession) {
        if (userService.save(user)) {
            flashSession.addFlashAttribute("success", "Success!");
        } else {
            flashSession.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/user/add";
    }

    @Override
    public String doAddWithImage(User user, RedirectAttributes flashSession, MultipartFile multipartFile) {
        return null;
    }

    @Override
    @GetMapping("/list")
    public String list(Model model,@RequestParam(name="page", defaultValue = "0") int page,@RequestParam(name="activePage", defaultValue = "0") int activePage) {
        int totalPage= userService.getPageUser(activePage).getTotalPages();
        if( page<0 || page>totalPage-1 ){
            Page<User> listUserPage = userService.getPageUser(activePage);
            model.addAttribute("listUserPage",listUserPage);
            model.addAttribute("activePage", activePage);
        }else {
            Page<User> listUserPage = userService.getPageUser(page);
            model.addAttribute("listUserPage",listUserPage);
            model.addAttribute("activePage", page);

        }
        return "admin/user/list";
    }

    @Override
    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id") long id, RedirectAttributes flashSession){
        if (userService.delete(id)) {
            flashSession.addFlashAttribute("success", "Success!");
        } else {
            flashSession.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/user/list";
    }

    @Override
    @GetMapping ("/edit")
    public String edit(Model model, @RequestParam(name = "id") long id) {
        System.out.println(id);
        User user= userService.getUserById(id);
        model.addAttribute("user",user);
        model.addAttribute("roles",userRoleService.getAll());
        return "admin/user/edit";
    }

    @Override
    @PostMapping ("/do-edit")
    public String doEdit(User user, RedirectAttributes flashSession) {
        if (userService.save(user)) {
            flashSession.addFlashAttribute("success", "Success!");
        } else {
            flashSession.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/user/list";
    }

    @Override
    public String doEditWithImage(User user, RedirectAttributes flashSession, MultipartFile multipartFile) {
        return null;
    }

}
