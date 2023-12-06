package com.holisticbabe.holisticbabemarketplace.Models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_comment;
    private String comment;
    private LocalDate dateOfComment;

    @ManyToOne
    private Comment parentComment;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private _User user;

    @ManyToOne
    @JoinColumn(name = "id_course")
    private Course course;

}
