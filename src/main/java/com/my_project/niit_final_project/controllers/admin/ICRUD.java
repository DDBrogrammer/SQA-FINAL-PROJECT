package com.my_project.niit_final_project.controllers.admin;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ICRUD <Obj>{
    public String add(Model model);

    public String doAdd(Obj obj, RedirectAttributes flashSession);
    public String doAddWithImage(Obj obj, RedirectAttributes flashSession,
                        @RequestParam(name = "img") MultipartFile multipartFile);
    public String list(Model model, @RequestParam(name = "page", defaultValue = "1") int page,@RequestParam(name="activePage", defaultValue = "0") int activePage);

    public String delete(@RequestParam(name = "id") long id, RedirectAttributes flashSession);

    public String edit(Model model, long id);

    public String doEdit(Obj obj, RedirectAttributes flashSession);
    public String doEditWithImage(Obj obj,RedirectAttributes flashSession,@RequestParam(name ="img") MultipartFile multipartFile);
}
