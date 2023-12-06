package com.holisticbabe.holisticbabemarketplace.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_category;
    @NotBlank(message = "Category name is required")
    @Size(min = 3, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$")
    private String name;
    private String description;
    private LocalDateTime dateCreated;

    @OneToOne
    @JoinColumn(name = "id_multi")
    private Multimedia image;

}
