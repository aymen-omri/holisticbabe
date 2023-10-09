package com.holisticbabe.holisticbabemarketplace.Models.Bootcamp;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Models.Shared.Category;
import com.holisticbabe.holisticbabemarketplace.Models.User._User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bootcamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bootcamp_id;
    private String name;
    private String description;
    private String location;
    private LocalDate start_date;
    private LocalDate end_date;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private _User host;
    @ManyToMany
    private List<_User> participants = new ArrayList<_User>();
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;
}
