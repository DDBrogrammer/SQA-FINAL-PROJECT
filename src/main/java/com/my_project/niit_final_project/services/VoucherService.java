package com.my_project.niit_final_project.services;

import com.my_project.niit_final_project.entities.Category;
import com.my_project.niit_final_project.entities.Product;
import com.my_project.niit_final_project.entities.ProductType;
import com.my_project.niit_final_project.entities.Voucher;
import com.my_project.niit_final_project.repositories.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class VoucherService {

    @Autowired
    VoucherRepository voucherRepository;

    public  <T> List<List<T>> getPages(Collection<T> c, Integer pageSize) {
        if (c == null)
            return Collections.emptyList();
        List<T> list = new ArrayList<T>(c);
        if (pageSize == null || pageSize <= 0 || pageSize > list.size())
            pageSize = list.size();
        int numPages = (int) Math.ceil((double)list.size() / (double)pageSize);
        List<List<T>> pages = new ArrayList<List<T>>(numPages);
        for (int pageNum = 0; pageNum < numPages;)
            pages.add(list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size())));
        return pages;
    }

//    public  List<List<Product>> getProductsByTypeId(int size, long id){
//        ProductType productType=getProductTypeById(id);
//        Collection<Category> categories=   productType.getCategories();
//        Collection<Product> products=new HashSet<Product>();
//        for (Category category:categories){
//            for(Product product: category.getProducts()){
//                products.add(product);
//            }
//        }
//        List<List<Product>> listPageProduct= getPages(products,size);
//        return listPageProduct;
//    }


    public  Long countTotal(){
        return voucherRepository.count();
    }

    public boolean save(Voucher voucher) {
        LocalDateTime localDate = LocalDateTime.now();
        voucher.setCreatedDate(localDate);
        try {
            voucherRepository.save(voucher);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean delete(long id) {
        try {
            voucherRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Voucher getVoucherById(Long id) {
        return voucherRepository.findById(id).get();
    }

    public Page<Voucher> getPageVoucherType(int page, int size ){
        Pageable pageable=  PageRequest.of(page,size);
        return voucherRepository.findAll(pageable);
    }
    public  List<Voucher> getAll(){
        List<Voucher> resultList=voucherRepository.findAll();
        Voucher baseVoucher=new Voucher();
        baseVoucher.setName("No voucher");
        baseVoucher.setSalePercentage(0.0);
        return voucherRepository.findAll();
    }
}
