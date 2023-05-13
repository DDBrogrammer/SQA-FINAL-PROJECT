package com.my_project.niit_final_project.controllers.admin;

import com.my_project.niit_final_project.entities.Banner;
import com.my_project.niit_final_project.entities.Category;
import com.my_project.niit_final_project.entities.Product;
import com.my_project.niit_final_project.services.BannerService;
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
@RequestMapping("/admin/banner")
public class BannerController {
    @Autowired
   private BannerService bannerService;

    @GetMapping("/add")
    public String add(Model model){
        Banner  banner=new Banner();
        model.addAttribute("banner",banner );
        return "admin/banner/add";
    }

    @PostMapping("/do-add")
    public String doAddWithImage(Banner banner, RedirectAttributes flashSession, @RequestParam(name = "img") MultipartFile multipartFile) {
        if (bannerService.save(banner, multipartFile)) {
            flashSession.addFlashAttribute("success", "Add successfully");
        } else {
            flashSession.addFlashAttribute("failed", "Add failed");
        }
        return "redirect:/admin/banner/add";
    }
    @GetMapping("/list")
    public String list(Model model, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "activePage", defaultValue = "0") int activePage) {
        int totalPage = bannerService.getPageBanner(activePage,10).getTotalPages();



        if (page < 0 || page > totalPage - 1) {

            Page<Banner> listBannerPage = bannerService.getPageBanner(activePage,10);
            model.addAttribute("listBannerPage", listBannerPage);
            model.addAttribute("activePage", activePage);
        } else {
            Page<Banner> listBannerPage = bannerService.getPageBanner(page,10);
            model.addAttribute("listBannerPage", listBannerPage);
            model.addAttribute("activePage", page);
        }

        return "admin/banner/list";
    }





    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id") long id, RedirectAttributes flashSession) {
        if (bannerService.delete(id)) {
            flashSession.addFlashAttribute("success", "Success!");
        } else {
            flashSession.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/banner/list";
    }


    @GetMapping("/edit")
    public String edit(Model model, long id) {
        Banner banner = bannerService.getBannerById(id);
        model.addAttribute("banner", banner);

        return "admin/banner/edit";
    }



    @PostMapping("/do-edit")
    public String doEditWithImage(Banner banner, RedirectAttributes flashSession, @RequestParam(name = "img") MultipartFile multipartFile) {
        if (bannerService.save(banner, multipartFile)) {
            flashSession.addFlashAttribute("success", "Add successfully");
        } else {
            flashSession.addFlashAttribute("failed", "Add failed");
        }
        return "redirect:/admin/banner/list";
    }
}
