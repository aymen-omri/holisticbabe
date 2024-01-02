package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.CourseDto;
import com.holisticbabe.holisticbabemarketplace.Dtos.ProductDto;
import com.holisticbabe.holisticbabemarketplace.Models.*;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductPromotionRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductShop;
import com.holisticbabe.holisticbabemarketplace.Services.ProductService;
import com.holisticbabe.holisticbabemarketplace.Services.PromotionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private  final PromotionService promotionService;

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

    @PostMapping("/add-with-items/category/{id_category}/user/{id_user}")
    public ResponseEntity<?> addProductWithItems(
           @PathVariable  Long id_user,
            @PathVariable Long id_category,
            @RequestBody ProductRequest productRequest
            ) {
        try {

            Product addedProduct = productService.addProductWithItems(productRequest,id_user, id_category);
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
    public ResponseEntity<Product> saveProductImages(
            @PathVariable Long productId,
            @RequestParam("images") List<MultipartFile> images
    ) {
        try {
            Product product = productService.saveProductImages(productId, images);
            return ResponseEntity.ok(product);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
    public ResponseEntity<List<ProductShop>> getShop() {
        return ResponseEntity.ok(productService.listProductShop());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ProductDto>> getUserProducts(@PathVariable long id) {
        try {
            List<ProductDto> productDtoList = productService.getUserProducts(id);
            return new ResponseEntity<>(productDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/shop/user/{id}")
    public ResponseEntity<List<ProductShop>> getUsershop(@PathVariable long id) {
        try {
            List<ProductShop> productList = productService.getUserProductsShop(id);
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/category/{idCategory}")
    public ResponseEntity<?> updateProductClient(@PathVariable Long id, @RequestBody Product updatedProduct,
                                           @PathVariable Long idCategory ) {
        Product updated = productService.updateProductClient(id, updatedProduct,idCategory);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }



    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ProductPromotionRequest>> getProductsByOwnerId(@PathVariable Long ownerId) {
        List<ProductPromotionRequest> products = productService.getProductsWithPromotionAndImagesByOwnerId(ownerId);

        if (!products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/count/{userId}")
    public long countUserProducts(@PathVariable long userId) {
        return productService.countUserProductsShop(userId);
    }

    @GetMapping("/{productId}/promotion")
    public ResponseEntity<Promotion> getPromotionByProductId(@PathVariable Long productId) {
        Promotion promotion = promotionService.getPromotionByProductId(productId);

        if (promotion != null) {
            return new ResponseEntity<>(promotion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/countPromotion/{userId}")
    public long countPromotionByUserId(@PathVariable long userId) {
        return productService.countPromotionByUserId(userId);
    }
}
