package com.holisticbabe.holisticbabemarketplace.Repositories;

import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);

    /** List<Product> findPopularProducts(int limit); */
    @Query("SELECT p FROM Product p ORDER BY p.dateCreated DESC")
    List<Product> findTopNByOrderByDateCreatedDesc(int limit);

    List<Product> findByCategoryName(String categoryName);

    //List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    //List<Product> findByPriceGreaterThanEqual(BigDecimal minPrice);

    //List<Product> findByPriceLessThanEqual(BigDecimal maxPrice);

    //List<Product> findBySize(String size);

    Product findByName(String name);

    @Query("SELECT (" +
            "p.id_product, p.name, p.price, c.name, COALESCE(AVG(r.value), 0), " +
            "COALESCE(MAX(pro.discount), 0), (SELECT i.url FROM Multimedia i WHERE i.product = p ORDER BY i.id_multi LIMIT 1), " +
            "p.quantityInStock) " +
            "FROM Product p " +
            "JOIN p.category c " +
            "LEFT JOIN p.reviews r " +
            "LEFT JOIN p.promotions pro " +
            "GROUP BY p.id_product, p.name, p.price, c.name, p.quantityInStock")
    List<Object[]> listProductShop();

}