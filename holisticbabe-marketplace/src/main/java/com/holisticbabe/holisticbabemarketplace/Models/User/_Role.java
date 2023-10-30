package com.holisticbabe.holisticbabemarketplace.Models.User;

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
public class _Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_role;
    private String roleName;

}
