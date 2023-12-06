package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Dtos.CategoryWithImageDTO;
import com.holisticbabe.holisticbabemarketplace.Models.Category;
import com.holisticbabe.holisticbabemarketplace.Requests.CategoryRequest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category save(CategoryRequest categoryRequest, MultipartFile image);

    Category getById(Long id);

    Category updateCategory(Long categoryId, CategoryRequest categoryRequest, MultipartFile newImage);
    void deleteById(Long id);

    List<Category> searchCategoriesByName(String searchQuery);

    int getProductCountInCategory(Long categoryId);

}

