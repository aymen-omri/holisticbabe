package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductShop;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product createProduct(Product product);
    void deleteProduct(Long id);

    /*List<Product> getPopularProducts(int limit);*/
    List<Product> getNewProducts(int limit);
    List<Product> getProductsInCategory(String name);

    /*product exist ;*/
    boolean productExists(Long productId);
    Product updateProduct(Long id, Product updatedProduct);
    Product addNewImage(Long productId, MultipartFile newImage) throws IOException;

   List<Multimedia>getProductImages(Long productId);
    List<Product> filterProductsByPrice(BigDecimal minPrice, BigDecimal maxPrice);
    List<Product> getProductsByPrice(String sortDirection);

    List<Product> getProductsBySize(String size) ;
    List<Product> searchProductsByName(String name);

    List<ProductShop> listProductShop();

    }
