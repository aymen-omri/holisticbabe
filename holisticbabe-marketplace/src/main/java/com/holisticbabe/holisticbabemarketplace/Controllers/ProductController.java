package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Constants.HBConstants;
import com.holisticbabe.holisticbabemarketplace.Dtos.ProductDto;
import com.holisticbabe.holisticbabemarketplace.Models.*;
import com.holisticbabe.holisticbabemarketplace.Requests.CategoryRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductItemRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductShop;
import com.holisticbabe.holisticbabemarketplace.Services.CategoryService;
import com.holisticbabe.holisticbabemarketplace.Services.MultimediaService;
import com.holisticbabe.holisticbabemarketplace.Services.ProductService;
import com.holisticbabe.holisticbabemarketplace.Services.ReviewService;
import com.holisticbabe.holisticbabemarketplace.Utils.HBUtils;

import com.holisticbabe.holisticbabemarketplace.Utlis.FileUploadService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final MultimediaService multimediaService;
    private final CategoryService categoryService;
    private final ReviewService reviewService;
    private final FileUploadService fileUploadService;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add-with-items/{id_category}")
    public ResponseEntity<?> addProductWithItems(
           /* @RequestParam(value = "id_user", required = false) Long id_user,*/
            @PathVariable Long id_category,
            @RequestBody ProductRequest productRequest
            ) {
        try {

            Product addedProduct = productService.addProductWithItems(productRequest, /* id_user*/ id_category);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error message");
        }
    }

    @PutMapping("/{id}/category/{idCategory}/promotion/{idPromotion}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct,
                                                @PathVariable Long idCategory,@PathVariable Long idPromotion ) {
        Product updated = productService.updateProduct(id, updatedProduct,idCategory,idPromotion);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }


    @PostMapping("/{productId}/images")
    public ResponseEntity<Product> addImages(
            @PathVariable Long productId,
            @RequestParam("images") MultipartFile images) throws IOException {
        Product updatedProduct = productService.addNewImage(productId, images);
        return ResponseEntity.ok(updatedProduct);
    }

    @PostMapping("/{productId}/addImage")
    public ResponseEntity<Product> addNewImage(
            @PathVariable Long productId,
            @RequestParam("newImage") MultipartFile newImage) throws IOException {
        Product updatedProduct = productService.addNewImage(productId, newImage);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping("/{productId}/listImages")
    public ResponseEntity<List<Multimedia>> getProductImages(@PathVariable Long productId) {
        List<Multimedia> images = productService.getProductImages(productId);
        if (!images.isEmpty()) {
            return ResponseEntity.ok(images);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if (productService.productExists(id)) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/new/{limit}")
    public ResponseEntity<List<ProductDto>> getNewProducts(int limit) {
        List<ProductDto> newProducts = productService.getNewProducts(limit);
        if (!newProducts.isEmpty()) {
            return ResponseEntity.ok(newProducts);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ProductDto>> getProductsInCategory(@PathVariable String categoryName) {
        List<ProductDto> products = productService.getProductsInCategory(categoryName);
        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    @GetMapping("/search")
    public ResponseEntity<ProductDto> searchProductsByName(@RequestParam("name") String name) {
        ProductDto products = productService.searchProductsByName(name);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/getShopProduct")
    public ResponseEntity<List<ProductShop>> getshop() {
        return ResponseEntity.ok(productService.listProductShop());
    }

}
