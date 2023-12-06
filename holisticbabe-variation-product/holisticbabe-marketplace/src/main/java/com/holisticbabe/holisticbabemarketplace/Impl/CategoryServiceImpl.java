package com.holisticbabe.holisticbabemarketplace.Impl;

import com.holisticbabe.holisticbabemarketplace.Dtos.CategoryWithImageDTO;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Repositories.CategoryRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.MultimediaRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.ProductRepository;
import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Category;
import com.holisticbabe.holisticbabemarketplace.Requests.CategoryRequest;
import com.holisticbabe.holisticbabemarketplace.Services.CategoryService;
import com.holisticbabe.holisticbabemarketplace.Utlis.FileUploadService;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

      Category savedCategory=  saveCategoryImages(category, image);
      return  savedCategory;
    }


    private Category saveCategoryImages(Category category, MultipartFile image) {
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
       Multimedia multimedia= multimediaRepository.save(imageToSave);
        category.setImage(multimedia);
        return categoryRepository.save(category);
    }
    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }


    @Override
    public Category updateCategory(Long categoryId, CategoryRequest categoryRequest, MultipartFile newImage) {
        Category existingCategory = categoryRepository.findById(categoryId).orElse(null);

        if (existingCategory == null) {
            throw new EntityNotFoundException("Category not found");
        }

      //  deleteOldImage(existingCategory);

            existingCategory.setName(categoryRequest.getName());
            existingCategory.setDescription(categoryRequest.getDescription());
            existingCategory.setDateCreated(categoryRequest.getDateCreated());

        return saveCategoryImages(existingCategory, newImage);
    }

    private void deleteOldImage(Category category) {
        Multimedia oldImage = multimediaRepository.findByCategory(category);
            multimediaRepository.delete(oldImage);

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