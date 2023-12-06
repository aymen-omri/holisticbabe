package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.ProductItem;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductItemRequest;
import com.holisticbabe.holisticbabemarketplace.Services.ProductItemService;
import com.holisticbabe.holisticbabemarketplace.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/product-items")
public class ProductItemController {
    @Autowired
    private ProductItemService productItemService;
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductItem> createProductItem(@RequestParam("sku") String sku,
            @RequestParam("quantityInStock") int quantityInStock,
            @RequestParam("price") BigDecimal price, @RequestParam("idProduct") Long productId,
            @RequestParam("variationOptionValues") List<String> variationOptionValues) {
        ProductItem productItem = new ProductItem();
        productItem.setSKU(sku);
        productItem.setQuantityInStock(quantityInStock);
        productItem.setPrice(price);
        ProductItem created = productItemService.createProductItem(productItem, productId, variationOptionValues);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{productId}/product-items")
    public ResponseEntity<List<ProductItem>> getProductItemsByProduct(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            List<ProductItem> productItems = productItemService.getProductItemsByProduct(product);
            return ResponseEntity.ok(productItems);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{productItemId}")
    public ResponseEntity<ProductItem> updateProductItem(
            @PathVariable Long productItemId,
            @RequestBody ProductItemRequest updatedProductItemRequest) {

        ProductItem updatedItem = productItemService.updateProductItem(productItemId, updatedProductItemRequest);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @GetMapping("/{productItemId}")
    public ResponseEntity<ProductItem> getProductItemById(@PathVariable Long productItemId) {
        ProductItem productItem = productItemService.getProductItemById(productItemId);
        return ResponseEntity.ok(productItem);
    }

    @GetMapping
    public ResponseEntity<List<ProductItem>> getAll() {
        return ResponseEntity.ok(productItemService.getAllProductItems());
    }

    @GetMapping("/count/{productId}")
    public ResponseEntity<Long> countProductItemsByProductId(@PathVariable Long productId) {
        long count = productItemService.countProductItemsByProduct(productId);
        return ResponseEntity.ok(count);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductItem(@PathVariable Long id) {
        productItemService.deleteProductItem(id);
        return ResponseEntity.noContent().build();
    }
}
