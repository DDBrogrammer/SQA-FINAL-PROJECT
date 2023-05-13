package com.my_project.niit_final_project.controllers.admin;

import com.my_project.niit_final_project.entities.Voucher;
import com.my_project.niit_final_project.services.VoucherService;
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
@RequestMapping("/admin/voucher" )
public class VoucherController implements ICRUD<Voucher> {
    @Autowired
    private VoucherService voucherService;
    @Override
    @GetMapping("/add")
    public String add(Model model) {
        Voucher voucher = new Voucher();
        model.addAttribute("voucher", voucher);
        return "admin/voucher/add";
    }

    @PostMapping("/do-add")
    public String doAdd(Voucher voucher, RedirectAttributes flashSession) {
        if (voucherService.save(voucher)) {
            flashSession.addFlashAttribute("success", "Success!");
        } else {
            flashSession.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/voucher/add";
    }

    @Override
    @GetMapping("/list")
    public String list(Model model, @RequestParam(name="page", defaultValue = "0") int page, @RequestParam(name="activePage", defaultValue = "0") int activePage) {
        int totalPage= voucherService.getPageVoucherType(activePage,10).getTotalPages();
        if( page<0 || page>totalPage-1 ){
            Page<Voucher> listVoucherPage = voucherService.getPageVoucherType(activePage,10);
            model.addAttribute("listVoucherPage",listVoucherPage);
            model.addAttribute("activePage", activePage);
        }else {
            Page<Voucher> listVoucherPage = voucherService.getPageVoucherType(page,10);
            model.addAttribute("listVoucherPage",listVoucherPage);
            model.addAttribute("activePage", page);
        }
        return "admin/voucher/list";
    }

    @Override
    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id") long id, RedirectAttributes flashSession){
        if (voucherService.delete(id)) {
            flashSession.addFlashAttribute("success", "Success!");
        } else {
            flashSession.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/voucher/list";
    }

    @Override
    @GetMapping ("/edit")
    public String edit(Model model, @RequestParam(name = "id") long id) {
        Voucher voucher= voucherService.getVoucherById(id);
        model.addAttribute("voucher",voucher);
        return "admin/voucher/edit";
    }

    @Override
    @PostMapping ("/do-edit")
    public String doEdit(Voucher voucher, RedirectAttributes flashSession) {
        if (voucherService.save(voucher)) {
            flashSession.addFlashAttribute("success", "Success!");
        } else {
            flashSession.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/voucher/list";
    }

    @Override
    public String doEditWithImage(Voucher voucher, RedirectAttributes flashSession, MultipartFile multipartFile) {
        return null;
    }

    @Override
    public String doAddWithImage(Voucher voucher, RedirectAttributes flashSession, MultipartFile multipartFile) {
        return null;
    }
}
