package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.List;
import java.time.*;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Dtos.PaymentMethodDto;
import com.holisticbabe.holisticbabemarketplace.Models.PaymentMethod;
import com.holisticbabe.holisticbabemarketplace.Repositories.PaymentMethodRepository;
import com.holisticbabe.holisticbabemarketplace.Services.PayementMethodService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PayementMethodService {

    private final ModelMapper modelMapper;
    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethodDto> findByUserId(long id) {
        return paymentMethodRepository.findByUserId(id)
                .stream().map(p -> modelMapper.map(p, PaymentMethodDto.class)).toList();
    }

    @Override
    public PaymentMethodDto findById(long id) {
        return modelMapper.map(paymentMethodRepository.findById(id), PaymentMethodDto.class);
    }

    @Override
    public void insertPaymentMethod(PaymentMethod paymentMethod) {
        if (!isValidCreditCard(paymentMethod.getAccountNumber())) {
            throw new IllegalArgumentException("Invalid credit card number");
        }
        if (paymentMethod.getExpiryDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Credit card expired.");
        }
        var pm = modelMapper.map(paymentMethod, PaymentMethod.class);
        paymentMethodRepository.save(pm);
    }

    @Override
    public void deletePaymentMethod(long id) {
        paymentMethodRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updatePaymentMethod(long idPaymentMethod,
            PaymentMethodDto paymentMethodDto) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(idPaymentMethod)
                .orElseThrow(() -> new EntityNotFoundException("No payment method found!"));
        if (!isValidCreditCard(paymentMethodDto.getAccountNumber())) {
            throw new IllegalArgumentException("Invalid credit card number");
        }
        if (paymentMethod.getExpiryDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Credit card expired.");
        }

        paymentMethod.setAccountNumber(paymentMethodDto.getAccountNumber());
        paymentMethod.setExpiryDate(paymentMethodDto.getExpiryDate());
        paymentMethod.setProvider(paymentMethodDto.getProvider());

    }

    private boolean isValidCreditCard(String cardNumber) {
        // Clean up the input by removing spaces and non-digit characters
        String cleanedCardNumber = cardNumber.replaceAll("[^0-9]", "");

        int sum = 0;
        boolean alternate = false;

        for (int i = cleanedCardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cleanedCardNumber.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }

}
