package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Dtos.PaymentMethodDto;
import com.holisticbabe.holisticbabemarketplace.Models.PaymentMethod;

public interface PayementMethodService {
    List<PaymentMethodDto> findByUserId(long id);

    PaymentMethodDto findById(long id);

    void insertPaymentMethod(PaymentMethod paymentMethod);

    void deletePaymentMethod(long id);

    void updatePaymentMethod(long idPaymentMethod,
            PaymentMethodDto paymentMethodDto);
}
