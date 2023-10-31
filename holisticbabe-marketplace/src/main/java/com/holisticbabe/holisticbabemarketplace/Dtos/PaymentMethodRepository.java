package com.holisticbabe.holisticbabemarketplace.Dtos;

import com.holisticbabe.holisticbabemarketplace.Models.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}
