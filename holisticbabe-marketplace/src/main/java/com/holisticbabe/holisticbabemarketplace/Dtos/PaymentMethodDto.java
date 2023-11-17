package com.holisticbabe.holisticbabemarketplace.Dtos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDto {

    private long id_payment_method;
    private String provider;
    private String accountNumber;
    private LocalDate expiryDate;
    private UserDto userDto;
    private PaymentTypeDto paymentTypeDto;
}