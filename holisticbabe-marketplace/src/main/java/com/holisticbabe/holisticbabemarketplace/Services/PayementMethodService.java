package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Dtos.PaymentMethodDto;

public interface PayementMethodService {
    List<PaymentMethodDto> findByUserId(long id);

    PaymentMethodDto findById(long id);

    void insertPaymentMethod(PaymentMethodDto paymentMethodDto);

    void deletePaymentMethod(long id);

    void updatePaymentMethod(long idPaymentType, long idPaymentMethod,
            PaymentMethodDto paymentMethodDto);
}
