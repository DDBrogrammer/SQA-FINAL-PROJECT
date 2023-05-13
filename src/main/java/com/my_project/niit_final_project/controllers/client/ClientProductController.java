package com.my_project.niit_final_project.controllers.client;
import com.my_project.niit_final_project.entities.Banner;
import com.my_project.niit_final_project.entities.Category;
import com.my_project.niit_final_project.entities.Product;
import com.my_project.niit_final_project.entities.ProductType;
import com.my_project.niit_final_project.services.BannerService;
import com.my_project.niit_final_project.services.CategoryService;
import com.my_project.niit_final_project.services.ProductService;
import com.my_project.niit_final_project.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private BannerService bannerService;
    @GetMapping("/home")
    private String getHome(Model model) {
        try {
            Page<Banner> listBanner=bannerService.getPageBanner(0,3);
            if(listBanner.getTotalElements()>=3){
            Banner banner_1=listBanner.getContent().get(0);
            Banner banner_2=listBanner.getContent().get(1);
            Banner banner_3=listBanner.getContent().get(2);
            model.addAttribute("banner_1", banner_1);
            model.addAttribute("banner_2", banner_2);
            model.addAttribute("banner_3", banner_3);
            }
            Page<Product> listNewProduct = productService.getPageNewProduct(0, 8);
            Page<Product> listSaleProduct = productService.getPageSaleProduct(0, 8);
            Page<ProductType> listProductType = productTypeService.getPageProductType(0, 6);

            model.addAttribute("listNewProduct", listNewProduct.iterator());
            model.addAttribute("listSaleProduct", listSaleProduct.iterator());
            model.addAttribute("listProductType", listProductType.iterator());
        } catch (ArithmeticException e) {
            System.out.println(e);
        } catch (Exception e){
            System.out.println(e);
        }finally
        { return "/client/home"; }
    }

    @GetMapping("/product/detail")
    private String getProductDetail(Model model, @RequestParam(name = "id") long id) {
        Product product = productService.getProductById(id);
        Page<ProductType> listProductType = productTypeService.getPageProductType(0, 6);
        model.addAttribute("listProductType", listProductType.iterator());
        model.addAttribute("product", product);
        return "/client/classify/product_detail";


    }

    @GetMapping("/category/product")
    private String getCategoryProduct(Model model, @RequestParam(name = "id") long id, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "activePage", defaultValue = "0") int activePage) {

        try {int totalPage = productService.getPageProductByCategoryId(page, 10, id).getTotalPages();
            //paging logic
            if (page > totalPage + 1) {
                Page<Product> listProductByCategory = productService.getPageProductByCategoryId(activePage, 10, id);
                Page<ProductType> listProductType = productTypeService.getPageProductType(0, 6);

                model.addAttribute("listProductType", listProductType.iterator());
                model.addAttribute("listProductByCategory", listProductByCategory);
                model.addAttribute("activePage", activePage);


            } else {
                Page<Product> listProductByCategory = productService.getPageProductByCategoryId(page, 10, id);
                Page<ProductType> listProductType = productTypeService.getPageProductType(0, 6);
                model.addAttribute("listProductType", listProductType.iterator());
                model.addAttribute("listProductByCategory", listProductByCategory);
                model.addAttribute("activePage", page);
            }} catch (Exception e){
            System.out.println(e);
        }
            finally{

            Category category = categoryService.getCategoryById(id);
            model.addAttribute("navCategory",category);
            model.addAttribute("id", id);
            return "/client/classify/category";}

    }
    @GetMapping("/type/product")
    private String getTypeProduct(Model model, @RequestParam(name = "id") long id, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "activePage", defaultValue = "0") int activePage) {
        try {

            int totalPage =productTypeService.getProductsByTypeId( 10, id).size();
            //paging logic
            if (page > totalPage + 1) {
                List<Product> listProductByType = productTypeService.getProductsByTypeId(10,id).get(activePage);
                model.addAttribute("listProductByType", listProductByType);//this is list of product for show
                model.addAttribute("activePage", activePage);
            } else {
                List<Product> listProductByType = productTypeService.getProductsByTypeId(10,id).get(page);
                model.addAttribute("listProductByType", listProductByType);//this is list of product for show
                model.addAttribute("activePage", page);
            }} catch (Exception e){
            System.out.println(e);
        }
        finally{
            Page<ProductType> listProductType = productTypeService.getPageProductType(0, 6);
            model.addAttribute("listProductType", listProductType.iterator());//this is list product for navigate
            ProductType productType = productTypeService.getProductTypeById(id);
            model.addAttribute("navType",productType);
            model.addAttribute("id", id);
            model.addAttribute("totalPage",productTypeService.getProductsByTypeId( 10, id).size() );
            return "/client/classify/type";}
    }

    @GetMapping("/search")
    private String   getSearchResult(Model model, @RequestParam(name = "keywords", defaultValue = "null") String keywords,
                                     @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "activePage", defaultValue = "0") int activePage) {
        try {int totalPage = productService.getPageSearchProduct(page, 10, keywords).getTotalPages();
            //paging logic
            if (page > totalPage + 1) {
                Page<Product> listProductSearch = productService.getPageSearchProduct(activePage, 10, keywords);
                Page<ProductType> listProductType = productTypeService.getPageProductType(0, 6);
                model.addAttribute("listProductType", listProductType.iterator());
                model.addAttribute("listProductSearch", listProductSearch);
                model.addAttribute("activePage", activePage);


            } else {
                Page<Product> listProductSearch = productService.getPageSearchProduct(page, 10, keywords);
                Page<ProductType> listProductType = productTypeService.getPageProductType(0, 6);
                model.addAttribute("listProductType", listProductType.iterator());
                model.addAttribute("listProductSearch", listProductSearch);
                model.addAttribute("activePage", page);
            }} catch (Exception e){
            System.out.println(e);
        }
        finally{
            model.addAttribute("keywords", keywords);
            return "client/search_result";
        }
       }

}
