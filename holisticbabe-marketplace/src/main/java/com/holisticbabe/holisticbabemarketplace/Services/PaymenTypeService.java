package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Dtos.PaymentTypeDto;

public interface PaymenTypeService {
    PaymentTypeDto findById(long id);

    List<PaymentTypeDto> findAll();

    void insertPaymentType(PaymentTypeDto paymentTypeDto);

    void deletePaymentType(long id);

    void updatePaymentType(long id, PaymentTypeDto paymentTypeDto);
}
