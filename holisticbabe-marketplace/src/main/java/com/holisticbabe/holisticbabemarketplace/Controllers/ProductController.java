package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Constants.HBConstants;
import com.holisticbabe.holisticbabemarketplace.Dtos.ProductDto;
import com.holisticbabe.holisticbabemarketplace.Models.*;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductItemRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductShop;
import com.holisticbabe.holisticbabemarketplace.Services.CategoryService;
import com.holisticbabe.holisticbabemarketplace.Services.MultimediaService;
import com.holisticbabe.holisticbabemarketplace.Services.ProductService;
import com.holisticbabe.holisticbabemarketplace.Services.ReviewService;
import com.holisticbabe.holisticbabemarketplace.Utils.HBUtils;

import com.holisticbabe.holisticbabemarketplace.Utlis.FileUploadService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("/add-with-items")
    public ResponseEntity<?> addProductWithItems(
            @RequestParam("id_user") Long id_user,
            @RequestParam("id_category") Long id_category,
            @RequestParam(value = "variationOptionValues", required = false) List<String> variationOptionValues,
            @RequestPart ProductRequest productRequest,
            @RequestPart List<MultipartFile> images
            ) {
        try {
            Product addedProduct = productService.addProductWithItems(productRequest, id_user, id_category,variationOptionValues,images);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error message");
        }
    }

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
/**
    // range Price
    @GetMapping("/priceRange")
    public ResponseEntity<List<ProductDto>> filterAndSortProductsByPrice(
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice) {
        List<ProductDto> filteredProducts = productService.filterProductsByPrice(minPrice, maxPrice);
        return ResponseEntity.ok(filteredProducts);
    }

    @GetMapping("/getPriceAscDes")
    public ResponseEntity<List<ProductDto>> getProductsByPrice(
            @RequestParam(name = "sort", required = false, defaultValue = "ascending") String sortDirection) {
        List<ProductDto> products = productService.getProductsByPrice(sortDirection);
        return ResponseEntity.ok(products);
    }

   /* @GetMapping("/getSize")
    public ResponseEntity<List<ProductDto>> getProductsBySize(@RequestParam(name = "size") String size) {
        List<ProductDto> products = productService.getProductsBySize(size);
        return ResponseEntity.ok(products);
    }
 */

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
