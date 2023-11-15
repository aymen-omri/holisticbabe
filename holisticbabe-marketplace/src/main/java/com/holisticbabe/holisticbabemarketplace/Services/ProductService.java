package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Dtos.ProductDto;
import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Models.ProductItem;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductItemRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductShop;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();

    Product getProductById(Long id);
    Product addProductWithItems(ProductRequest productRequest,Long id_user,Long id_category,List<String>variationOptionValues,List<MultipartFile> images);

    void deleteProduct(Long id);

    /* List<Product> getPopularProducts(int limit); */
    List<ProductDto> getNewProducts(int limit);

    List<ProductDto> getProductsInCategory(String name);

    /* product exist ; */
    boolean productExists(Long productId);

    Product updateProduct(Long id, Product updatedProduct);

    Product addNewImage(Long productId, MultipartFile newImage) throws IOException;

    List<Multimedia> getProductImages(Long productId);

    //List<ProductDto> filterProductsByPrice(BigDecimal minPrice, BigDecimal maxPrice);

   // List<ProductDto> getProductsByPrice(String sortDirection);

   // List<ProductDto> getProductsBySize(String size);

    ProductDto searchProductsByName(String name);

    List<ProductShop> listProductShop();

}
