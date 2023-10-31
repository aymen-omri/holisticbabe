package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category save(Category category);
    Category getById(Long id);

    Category updateCategory(Long id, Category updatedCategory);
    void deleteById(Long id);
    List<Category> searchCategoriesByName(String searchQuery);

    int getProductCountInCategory(Long categoryId);



}
