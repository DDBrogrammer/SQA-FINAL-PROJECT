package com.my_project.niit_final_project.services;

import com.my_project.niit_final_project.entities.Banner;
import com.my_project.niit_final_project.entities.Product;
import com.my_project.niit_final_project.repositories.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service

public class BannerService {
    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private UploadService uploadService;


    public boolean save(Banner banner, MultipartFile uploadImage) {

        try {
            //upload ảnh
            if (uploadImage != null) {
                //tiến hành upload
                String uploadPath = uploadService.upload(uploadImage);
                if (uploadPath != null) {
                    banner.setPicture(uploadPath);
                } else {
                    return false;
                }
            }
            bannerRepository.save(banner);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean delete(long id) {
        try {
            bannerRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Banner getBannerById(long id) {
        return bannerRepository.findById(id).get();
    }

    public Page<Banner> getPageBanner(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bannerRepository.findAll(pageable);
    }






}
