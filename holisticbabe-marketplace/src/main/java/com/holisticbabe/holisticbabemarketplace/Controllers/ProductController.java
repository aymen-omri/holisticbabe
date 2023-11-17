package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Models.Review;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductShop;
import com.holisticbabe.holisticbabemarketplace.Services.ProductService;
import com.holisticbabe.holisticbabemarketplace.Services.ReviewService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;

    @GetMapping
    public List<Product> getAllProducts() {
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

    @PostMapping("/add")
    public ResponseEntity<?> createProductWithImages(
            @RequestParam("id_user") Long id_user,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam("id_category") Long id_category,
            @RequestPart List<MultipartFile> images){
        try {
            Product savedProduct = productService.createProduct(name , description , price , id_user , id_category , images);
            if(savedProduct == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Problem while inserting product!");
            }
            return ResponseEntity.ok(savedProduct);

        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    /**
     * @PostMapping
     *              public ResponseEntity<Product> createProduct(@RequestBody
     *              Product product) {
     *              // Créez un nouveau produit
     *              Product newProduct = productService.createProduct(product);
     *              return ResponseEntity.ok(newProduct);
     *              }
     * 
     * 
     *              @PostMapping("/{productId}/uploadImage")
     *              public ResponseEntity<String> uploadImage(
     * @PathVariable Long productId,
     * @RequestParam MultipartFile file
     * 
     *               ) throws IOException {
     * 
     *               Product product = productService.getProductById(productId);
     *               if (product == null) {
     *               return
     *               ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not
     *               found");
     *               }
     * 
     *               // Utilisez le service File pour gérer l'upload de l'image au
     *               produit
     *               fileService.uploadImage(file,product);
     * 
     *               return ResponseEntity.ok("Image uploaded successfully");
     *               }
     * 
     */

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product updated = productService.updateProduct(id, updatedProduct);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Product updated successfully");
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
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (productService.productExists(id)) {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product with ID " + id + " has been deleted.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @GetMapping("/popular")
     * public ResponseEntity<List<Product>>
     * getPopularProducts(@RequestParam("limit") int limit) {
     * List<Product> popularProducts = productService.getPopularProducts(limit);
     * if (!popularProducts.isEmpty()) {
     * return ResponseEntity.ok(popularProducts);
     * } else {
     * return ResponseEntity.noContent().build();
     * }
     * }
     */

    @GetMapping("/new/{limit}")
    public ResponseEntity<List<Product>> getNewProducts(int limit) {
        List<Product> newProducts = productService.getNewProducts(limit);
        if (!newProducts.isEmpty()) {
            return ResponseEntity.ok(newProducts);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Product>> getProductsInCategory(@PathVariable String categoryName) {
        List<Product> products = productService.getProductsInCategory(categoryName);
        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    // range Price
    @GetMapping("/priceRange")
    public ResponseEntity<List<Product>> filterAndSortProductsByPrice(
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice) {
        List<Product> filteredProducts = productService.filterProductsByPrice(minPrice, maxPrice);
        return ResponseEntity.ok(filteredProducts);
    }

    @GetMapping("/getPriceAscDes")
    public ResponseEntity<List<Product>> getProductsByPrice(
            @RequestParam(name = "sort", required = false, defaultValue = "ascending") String sortDirection) {
        List<Product> products = productService.getProductsByPrice(sortDirection);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getSize")
    public ResponseEntity<List<Product>> getProductsBySize(@RequestParam(name = "size") String size) {
        List<Product> products = productService.getProductsBySize(size);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam("name") String name) {
        List<Product> products = productService.searchProductsByName(name);

        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable Long productId) {
        List<Review> reviews = reviewService.getReviewsByProductId(productId);
        if (reviews.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(reviews);
        }
    }

    @PostMapping("/{productId}/reviews")
    public ResponseEntity<Review> createReview(
            @PathVariable Long productId,
            @RequestBody Review review) {
        Review createdReview = reviewService.createReviewInProduct(productId, review);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @PutMapping("/{productId}/reviews/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId,
            @RequestBody Review updatedReview) {
        Review updated = reviewService.updateReviewInProduct(productId, reviewId, updatedReview);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{productId}/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId) {
        reviewService.deleteReviewInProduct(productId, reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{productId}/rating")
    public ResponseEntity<Double> getProductRating(@PathVariable Long productId) {
        double productRating = reviewService.calculateProductRating(productId);

        if (productRating >= 0.0) {
            return ResponseEntity.ok(productRating);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{productId}/countReviews")
    public ResponseEntity<Integer> getcountReviews(@PathVariable Long productId) {
        int countedReviews = reviewService.countReviews(productId);
        if (countedReviews >= 0) {
            return ResponseEntity.ok(countedReviews);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getShopProduct")
    public ResponseEntity<List<ProductShop>> getshop() {
        return ResponseEntity.ok(productService.listProductShop());
    }

}
