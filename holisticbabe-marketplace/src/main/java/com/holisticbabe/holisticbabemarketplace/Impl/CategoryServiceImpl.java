package com.holisticbabe.holisticbabemarketplace.Impl;

import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Repositories.CategoryRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.MultimediaRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.ProductRepository;
import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Category;
import com.holisticbabe.holisticbabemarketplace.Requests.CategoryRequest;
import com.holisticbabe.holisticbabemarketplace.Services.CategoryService;
import com.holisticbabe.holisticbabemarketplace.Utlis.FileUploadService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private MultimediaRepository multimediaRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(CategoryRequest categoryRequest, MultipartFile image) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setDateCreated(LocalDateTime.now());

        Category saveCategory = categoryRepository.save(category);
        saveCategoryImages(saveCategory, image);

        return categoryRepository.findById(saveCategory.getId_category()).orElse(null);
    }


    private void saveCategoryImages(Category category, MultipartFile image) {
            String url = null;
            try {
                url = fileUploadService.uploadFile(image, "category-multimedia");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Multimedia imageToSave = new Multimedia();
            imageToSave.setName(image.getOriginalFilename());
            imageToSave.setType(image.getContentType());
            imageToSave.setUrl(url);
            imageToSave.setCategory(category);
            multimediaRepository.save(imageToSave);
        ;
    }
    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }


    @Override
    @Transactional
    public Category updateCategory(Long categoryId, CategoryRequest categoryRequest, MultipartFile newImage) {
        Category existingCategory = categoryRepository.findById(categoryId).orElse(null);

        if (existingCategory == null) {
            throw new EntityNotFoundException("Category not found");
        }

        deleteOldImage(existingCategory);

        if (!StringUtils.isEmpty(categoryRequest.getName())) {
            existingCategory.setName(categoryRequest.getName());
        }

        if (!StringUtils.isEmpty(categoryRequest.getDescription())) {
            existingCategory.setDescription(categoryRequest.getDescription());
        }

        if (categoryRequest.getDateCreated() != null) {
            existingCategory.setDateCreated(categoryRequest.getDateCreated());
        }

        // Save new image
        if (newImage != null) {
            saveCategoryImages(existingCategory, newImage);
        }

        return categoryRepository.save(existingCategory);
    }

    private void deleteOldImage(Category category) {
        Multimedia oldImage = multimediaRepository.findByCategory(category);
        if (oldImage != null) {
            multimediaRepository.delete(oldImage);
        }
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);

    }


     // search Category by name

    @Override
    public List<Category> searchCategoriesByName(String searchQuery) {
        return categoryRepository.findByNameContainingIgnoreCase(searchQuery);
    }

     // count product in category

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