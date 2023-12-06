package com.holisticbabe.holisticbabemarketplace.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.holisticbabe.holisticbabemarketplace.Models.Enrollment;

import java.util.List;

public interface EnrollementRepository extends JpaRepository<Enrollment, Long> {

    @Query("select e from Enrollment e where e.user.id_user=?1")
    List<Enrollment> findByUserId(long id);

}
