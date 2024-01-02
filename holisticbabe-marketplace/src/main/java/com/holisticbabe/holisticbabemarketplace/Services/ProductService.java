package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Dtos.ProductDto;
import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;

import com.holisticbabe.holisticbabemarketplace.Requests.ProductPromotionRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductShop;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();

    Product getProductById(Long id);

    Product addProductWithItems(ProductRequest productRequest, Long id_user , Long id_category);

    void deleteProduct(Long id);

    Product saveProductImages(Long productId, List<MultipartFile> images) throws IOException;

    List<ProductDto> getNewProducts(int limit);

    List<ProductDto> getProductsInCategory(String name);

    /* product exist ; */
    boolean productExists(Long productId);

    Product updateProduct(Long id, Product updatedProduct, Long CategoryId, Long PromotionId);

    Product addNewImage(Long productId, MultipartFile newImage) throws IOException;

    List<Multimedia> getProductImages(Long productId);

    ProductDto searchProductsByName(String name);

    List<ProductShop> listProductShop();

    List<ProductDto> getUserProducts(long id);
    List<ProductShop> getUserProductsShop(long id);

    Product updateProductClient(Long id, Product updatedProduct,Long CategoryId);

    List<ProductPromotionRequest> getProductsWithPromotionAndImagesByOwnerId(Long ownerId);
    long countUserProductsShop(long id);
    long countPromotionByUserId(long userId);
}
