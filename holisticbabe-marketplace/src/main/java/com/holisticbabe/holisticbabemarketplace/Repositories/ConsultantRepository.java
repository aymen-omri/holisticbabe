package com.holisticbabe.holisticbabemarketplace.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

import com.holisticbabe.holisticbabemarketplace.Models.Consultant;

public interface ConsultantRepository extends JpaRepository<Consultant, Long> {

    @Query("select c from Consultant c where c.user.id_user = ?1")
    Optional<Consultant> getConsultantByUserId(long id);

}
