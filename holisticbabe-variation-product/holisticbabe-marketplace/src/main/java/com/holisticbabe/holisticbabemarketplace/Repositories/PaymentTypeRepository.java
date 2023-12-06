package com.holisticbabe.holisticbabemarketplace.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.holisticbabe.holisticbabemarketplace.Models.PaymentType;

public interface PaymentTypeRepository extends JpaRepository<PaymentType , Long> {
    
}
