package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Dtos.PaymentMethodDto;
import com.holisticbabe.holisticbabemarketplace.Models.PaymentMethod;
import com.holisticbabe.holisticbabemarketplace.Models.PaymentType;
import com.holisticbabe.holisticbabemarketplace.Repositories.PaymentMethodRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.PaymentTypeRepository;
import com.holisticbabe.holisticbabemarketplace.Services.PayementMethodService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PayementMethodService {

    private final ModelMapper modelMapper;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentTypeRepository paymentTypeRepository;

    @Override
    public List<PaymentMethodDto> findByUserId(long id) {
        return paymentMethodRepository.findByUserId(id).stream().map(p -> modelMapper.map(p, PaymentMethodDto.class))
                .toList();
    }

    @Override
    public PaymentMethodDto findById(long id) {
        return modelMapper.map(paymentMethodRepository.findById(id), PaymentMethodDto.class);
    }

    @Override
    public void insertPaymentMethod(PaymentMethodDto paymentMethodDto) {
        paymentMethodRepository.save(modelMapper.map(paymentMethodDto, PaymentMethod.class));
    }

    @Override
    public void deletePaymentMethod(long id) {
        paymentMethodRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updatePaymentMethod(long idPaymentType, long idPaymentMethod,
            PaymentMethodDto paymentMethodDto) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(idPaymentMethod)
                .orElseThrow(() -> new EntityNotFoundException("No payment method found!"));
        PaymentType paymentType = paymentTypeRepository.findById(idPaymentType)
                .orElseThrow(() -> new EntityNotFoundException("No payment type found!"));

        paymentMethod.setAccountNumber(paymentMethodDto.getAccountNumber());
        paymentMethod.setExpiryDate(paymentMethodDto.getExpiryDate());
        paymentMethod.setProvider(paymentMethodDto.getProvider());
        paymentMethod.setPaymentType(paymentType);

    }

}
