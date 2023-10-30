package com.holisticbabe.holisticbabemarketplace.Services.AdminCategoryService;

import com.holisticbabe.holisticbabemarketplace.Dtos.CategoryRepository;
import com.holisticbabe.holisticbabemarketplace.Dtos.ProductRepository;
import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }


    @Override
    public Category updateCategory(Long id, Category updatedCategory) {
        if (!categoryRepository.existsById(id)) {
            return null;
        }

        updatedCategory.setId_category(id);
        return categoryRepository.save(updatedCategory);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);

    }

    /**
     * search Category by name
     */
    @Override
    public List<Category> searchCategoriesByName(String searchQuery) {
        return categoryRepository.findByNameContainingIgnoreCase(searchQuery);
    }

    /**
     * count product in category
     */
    @Override
    public int getProductCountInCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            List<Product> productsInCategory = productRepository.findByCategory(category);
            return productsInCategory.size();
        } else {
            return 0;
        }
    }
}



