package com.holisticbabe.holisticbabemarketplace.Services;

import org.springframework.http.ResponseEntity;

import com.holisticbabe.holisticbabemarketplace.Dtos.SocialMediaDto;
import com.holisticbabe.holisticbabemarketplace.Models.SocialMedia;

public interface SocialMediaService {
    SocialMediaDto getById(long id);

    SocialMediaDto getByUserId(long id);

    void save(SocialMedia socialMedia);

    ResponseEntity<?> update(SocialMediaDto socialMedia, long id);

}
