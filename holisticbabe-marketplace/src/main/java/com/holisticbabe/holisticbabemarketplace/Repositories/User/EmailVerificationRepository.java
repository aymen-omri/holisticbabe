package com.holisticbabe.holisticbabemarketplace.Repositories.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.holisticbabe.holisticbabemarketplace.Models.User.EmailVerification;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

    @Query("select e from EmailVerification e where e.user.email = ?1")
    Optional<EmailVerification> findByUserEmail(String email);

}
