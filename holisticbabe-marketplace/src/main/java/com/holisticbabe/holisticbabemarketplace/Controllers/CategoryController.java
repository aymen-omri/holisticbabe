package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Models.Category;
import com.holisticbabe.holisticbabemarketplace.Requests.CategoryRequest;
import com.holisticbabe.holisticbabemarketplace.Services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestParam("name") String name , @RequestParam("description") String description ,@RequestPart ("image")MultipartFile image) {
        try {
            CategoryRequest categoryRequest = new CategoryRequest() ; 
            categoryRequest.setName(name);
            categoryRequest.setDescription(description);
            Category createdCategory = categoryService.save(categoryRequest,image);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long categoryId,
            @RequestPart CategoryRequest categoryRequest,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Category updatedCategory = categoryService.updateCategory(categoryId, categoryRequest, image);
            return ResponseEntity.ok(updatedCategory);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Category>> searchCategories(@RequestParam("name") String searchQuery) {
        List<Category> categories = categoryService.searchCategoriesByName(searchQuery);
        if (!categories.isEmpty()) {
            return ResponseEntity.ok(categories);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{categoryId}/product-count")
    public ResponseEntity<Integer> getProductCountInCategory(@PathVariable Long categoryId) {
        int productCount = categoryService.getProductCountInCategory(categoryId);
        return ResponseEntity.ok(productCount);
    }

}