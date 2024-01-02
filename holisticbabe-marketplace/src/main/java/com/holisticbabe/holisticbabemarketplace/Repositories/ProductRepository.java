package com.holisticbabe.holisticbabemarketplace.Repositories;

import com.holisticbabe.holisticbabemarketplace.Models.Course;
import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);

    @Query("SELECT p FROM Product p ORDER BY p.dateCreated DESC")
    List<Product> findTopNByOrderByDateCreatedDesc(int limit);

    List<Product> findByCategoryName(String categoryName);

    Product findByName(String name);

    @Query("SELECT p.id_product, p.name, p.price, c.name, COALESCE(AVG(r.value), 0), " +
            "COALESCE(MAX(pro.discount), 0), " +
            "(SELECT i.url FROM Multimedia i WHERE i.product = p ORDER BY i.id_multi LIMIT 1), " +
            "p.quantityInStock " +
            "FROM Product p " +
            "JOIN p.category c " +
            "LEFT JOIN p.reviews r " +
            "LEFT JOIN p.promotion pro " +
            "GROUP BY p.id_product, p.name, p.price, c.name, p.quantityInStock")
    List<Object[]> listProductShop();


    @Query("select p from Product p where p.owner.id_user=?1")
    List<Product> findProductsByIdUser(long id);


    @Query("SELECT DISTINCT p, (SELECT i.url FROM Multimedia i WHERE i.product = p ORDER BY i.id_multi LIMIT 1) " +
            "FROM Product p " +
            "LEFT JOIN FETCH p.promotion " +
            "LEFT JOIN FETCH p.owner " +
            "WHERE p.owner.id_user = :ownerId")
    List<Object[]> findProductsWithPromotionAndImagesByOwnerId(@Param("ownerId") Long ownerId);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.owner.id_user = :userId")
    Long countProductsByIdUser(@Param("userId") long userId);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.owner.id_user = :userId AND p.promotion.discount IS NOT NULL")
    Long countPromotionByUserId(long userId);

    @Modifying
    @Query("UPDATE Product p SET p.promotion = NULL WHERE p.promotion.id_promotion = :promotionId")
    void updatePromotionIdToNull(@Param("promotionId") Long promotionId);
}