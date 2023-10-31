package com.holisticbabe.holisticbabemarketplace.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.holisticbabe.holisticbabemarketplace.Models.SocialMedia;

public interface SocialMediaRepository extends JpaRepository<SocialMedia, Long> {

    @Query("select s from SocialMedia s where s.user.id_user = ?1")
    Optional<SocialMedia> getSocialMediaByIdUser(long id);

}
