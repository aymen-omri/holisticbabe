package com.holisticbabe.holisticbabemarketplace.Repositories.User;

import org.springframework.data.jpa.repository.JpaRepository;

import com.holisticbabe.holisticbabemarketplace.Models.User.EmailVerification;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

}
