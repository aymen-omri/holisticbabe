package com.holisticbabe.holisticbabemarketplace.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_enrollment;
    private LocalDate enrollmentDate;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private _User user;
    @ManyToOne
    @JoinColumn(name = "id_course")
    private Course course;
    @OneToMany
    List<LessonProgress> lessonsProgress = new ArrayList<>();

}
