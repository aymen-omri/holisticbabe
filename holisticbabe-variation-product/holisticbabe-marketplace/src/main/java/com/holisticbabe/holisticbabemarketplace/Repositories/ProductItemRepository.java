package com.holisticbabe.holisticbabemarketplace.Repositories;

import com.holisticbabe.holisticbabemarketplace.Dtos.ProductDto;
import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductItemRepository extends JpaRepository<ProductItem,Long> {
    List<ProductItem> findByProduct(Product product);
    @Query("SELECT COUNT(pi) FROM ProductItem pi WHERE pi.product.id_product = :productId")
    long countProductItems(@Param("productId") Long productId);

}
