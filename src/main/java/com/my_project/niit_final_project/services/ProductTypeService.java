package com.my_project.niit_final_project.services;

import com.my_project.niit_final_project.entities.Category;
import com.my_project.niit_final_project.entities.Product;
import com.my_project.niit_final_project.entities.ProductType;

import com.my_project.niit_final_project.repositories.ProductTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductTypeService {

    @Autowired
    ProductTypeRepository productTypeRepository;

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


    public  List<List<Product>> getProductsByTypeId(int size,long id){
        ProductType productType=getProductTypeById(id);
        Collection<Category> categories=   productType.getCategories();
        Collection<Product> products=new HashSet<Product>();
          for (Category category:categories){
              for(Product product: category.getProducts()){
                  products.add(product);
              }
          }
        List<List<Product>> listPageProduct= getPages(products,size);
        return listPageProduct;

    }


    public  Long countTotal(){
        return productTypeRepository.count();
    }

    public boolean save(ProductType productType) {
        LocalDateTime localDate = LocalDateTime.now();

        productType.setCreatedDate(localDate);
        try {
            productTypeRepository.save(productType);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean delete(long id) {
        try {
            productTypeRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public ProductType getProductTypeById(long id) {
        return productTypeRepository.findById(id).get();
    }

    public Page<ProductType> getPageProductType(int page,int size ){
        Pageable pageable=  PageRequest.of(page,size);
        return productTypeRepository.findAll(pageable);
    }
    public  List<ProductType> getAll(){
        return productTypeRepository.findAll();
    }

}
