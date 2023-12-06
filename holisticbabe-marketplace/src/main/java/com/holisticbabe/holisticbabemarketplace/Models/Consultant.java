package com.holisticbabe.holisticbabemarketplace.Models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Consultant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_consultant;
    private String description;
    private int approved;
    private LocalDate date;

    @OneToOne
    private _User user;

}
