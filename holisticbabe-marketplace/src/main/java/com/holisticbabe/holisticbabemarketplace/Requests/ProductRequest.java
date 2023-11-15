package com.holisticbabe.holisticbabemarketplace.Requests;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private long id_product;
    private String name;

    private String ShortDescription;
    private String description;
    private LocalDateTime dateCreated;
    // List<ProductItemRequest> productItemRequestList;
    List<ProductItemRequest> productItems;

}
