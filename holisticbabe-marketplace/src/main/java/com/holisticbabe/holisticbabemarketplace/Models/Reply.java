package com.holisticbabe.holisticbabemarketplace.Models;

import com.holisticbabe.holisticbabemarketplace.Dtos.ReplyDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDate dateCreated;
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private _User user;

    public ReplyDto getDto() {
        ReplyDto dto = new ReplyDto();
        dto.setId(this.getId());
        dto.setReviewId(this.getReview().getId_review());
        dto.setContent(this.getContent());
        dto.setUserId(user.getId_user());
        dto.setDateCreated(LocalDate.now());
        dto.setUserName(user.getLastName());
        if (user.getImage() != null) {
            dto.setImage(user.getImage().getUrl());
        }
        return dto;
    }
}
