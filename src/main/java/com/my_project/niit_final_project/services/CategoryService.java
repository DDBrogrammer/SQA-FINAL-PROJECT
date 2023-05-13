package com.my_project.niit_final_project.services;

import com.my_project.niit_final_project.entities.Category;
import com.my_project.niit_final_project.entities.ProductType;
import com.my_project.niit_final_project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public  Long countTotal(){
        return categoryRepository.count();
    }
    public boolean save(Category category) {
        LocalDateTime localDate = LocalDateTime.now();

        category.setCreatedDate(localDate);
        try {
            categoryRepository.save(category);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean delete(long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Category getCategoryById(long id) {
        return categoryRepository.findById(id).get();
    }

    public Page<Category> getPageCategory(int page ,int size){
        Pageable pageable=  PageRequest.of(page,size);
        return categoryRepository.findAll(pageable);
    }
    public  Iterable<Category> getAll(){

        return categoryRepository.findAll();
    }
}
