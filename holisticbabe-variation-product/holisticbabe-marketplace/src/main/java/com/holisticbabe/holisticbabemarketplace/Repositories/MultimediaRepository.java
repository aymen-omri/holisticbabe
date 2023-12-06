package com.holisticbabe.holisticbabemarketplace.Repositories;

import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Models.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import org.springframework.data.repository.query.Param;

public interface MultimediaRepository extends JpaRepository<Multimedia, Long> {

    @Query("select m from Multimedia m where m.consultant.id_consultant = ?1")
    List<Multimedia> getFilesByConsultantId(long id);

    @Query("select m from Multimedia m where m.vendor.id_vendor = ?1")
    List<Multimedia> getFilesByVendorId(long id);
    Multimedia findByCategory(Category category);
    @Modifying
    @Transactional
    @Query("DELETE FROM Multimedia m WHERE m.product.id_product = :productId")
    void deleteByProductId(@Param("productId") Long productId);
}
