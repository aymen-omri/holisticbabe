package com.holisticbabe.holisticbabemarketplace.Dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String name;
    private String ShortDescription;
    private String description;
    private BigDecimal price;
    private int status;
    private LocalDateTime dateCreated;
    private int quantityInStock;
    private String sku;
    private Date dateProduction;
    private CategoryDto category;
   // private UserDto instructor;


}
