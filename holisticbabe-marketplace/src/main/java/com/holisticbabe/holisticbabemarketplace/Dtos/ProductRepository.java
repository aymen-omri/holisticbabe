package com.holisticbabe.holisticbabemarketplace.Dtos;

import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Category;
import com.holisticbabe.holisticbabemarketplace.Models.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);

/**    List<Product> findPopularProducts(int limit); */
     @Query("SELECT p FROM Product p ORDER BY p.dateCreated DESC")
     List<Product> findTopNByOrderByDateCreatedDesc(int limit);

    List<Product> findByCategoryName(String categoryName);

    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    List<Product> findByPriceGreaterThanEqual(BigDecimal minPrice);
    List<Product> findByPriceLessThanEqual(BigDecimal maxPrice);

    List<Product> findBySize(String size);
    List<Product> findByName(String name);

    @Query("SELECT  p, m.name AS imageName, c.name AS categoryName, r.value AS rating, pro.discount AS discountPrice, " +
            "p.name AS productName, p.price AS productPrice, p.size AS productSize " +
            "FROM Product p " +
            "JOIN p.images m " +
            "JOIN p.category c " +
            "JOIN  p.reviews r " +
            "JOIN  p.promotions pro "
            )
    List<Product> listProductShop();




}