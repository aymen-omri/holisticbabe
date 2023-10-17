package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Dtos.PaymentTypeDto;
import com.holisticbabe.holisticbabemarketplace.Models.PaymentType;
import com.holisticbabe.holisticbabemarketplace.Repositories.PaymentTypeRepository;
import com.holisticbabe.holisticbabemarketplace.Services.PaymenTypeService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymenTypeServiceImpl implements PaymenTypeService {

    private final ModelMapper modelMapper;
    private final PaymentTypeRepository paymentTypeRepository;

    @Override
    public PaymentTypeDto findById(long id) {
        return modelMapper.map(paymentTypeRepository.findById(id).get(), PaymentTypeDto.class);
    }

    @Override
    public List<PaymentTypeDto> findAll() {
        return paymentTypeRepository
                .findAll()
                .stream()
                .map(country -> modelMapper.map(country, PaymentTypeDto.class))
                .toList();
    }

    @Override
    public void insertPaymentType(PaymentTypeDto paymentTypeDto) {
        paymentTypeRepository.save(modelMapper.map(paymentTypeDto, PaymentType.class));

    }

    @Override
    public void deletePaymentType(long id) {
        paymentTypeRepository.deleteById(id);

    }

    @Override
    public void updatePaymentType(long id, PaymentTypeDto paymentTypeDto) {
        PaymentType paymentType = paymentTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country doesn't exist!"));
        paymentType.setTypeName(paymentType.getTypeName());
    }

}
