package com.holisticbabe.holisticbabemarketplace.Models.Shared;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Multimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_multi;
    private String url;
    private String type;
    private String name;
    private String duration ;
}
