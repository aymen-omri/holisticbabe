package com.holisticbabe.holisticbabemarketplace.Dtos;

import com.holisticbabe.holisticbabemarketplace.Models.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
}