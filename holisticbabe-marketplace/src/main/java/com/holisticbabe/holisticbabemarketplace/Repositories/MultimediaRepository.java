package com.holisticbabe.holisticbabemarketplace.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.holisticbabe.holisticbabemarketplace.Models.Category;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;

public interface MultimediaRepository extends JpaRepository<Multimedia, Long> {

    @Query("select m from Multimedia m where m.consultant.id_consultant = ?1")
    List<Multimedia> getFilesByConsultantId(long id);

    @Query("select m from Multimedia m where m.vendor.id_vendor = ?1")
    List<Multimedia> getFilesByVendorId(long id);

    Multimedia findByCategory(Category category);

    @Query("select m from Multimedia m where m.course.id_course = ?1")
    List<Multimedia> getCourseFiles(long id);

}
