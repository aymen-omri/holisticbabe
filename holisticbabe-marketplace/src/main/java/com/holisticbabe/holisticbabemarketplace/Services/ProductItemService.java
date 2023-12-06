package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.ProductItem;
import com.holisticbabe.holisticbabemarketplace.Requests.ProductItemRequest;

import java.util.List;

public interface ProductItemService {
    List<ProductItem> getAllProductItems();

    ProductItem createProductItem(ProductItem productItem, Long productId, List<String> variationOptionValues);

    ProductItem updateProductItem(Long productItemId, ProductItemRequest updatedProductItemRequest);

    void deleteProductItem(Long id);

    ProductItem getProductItemById(Long id);

    List<ProductItem> getProductItemsByProduct(Product product);

    long countProductItemsByProduct(Long productid);
}
