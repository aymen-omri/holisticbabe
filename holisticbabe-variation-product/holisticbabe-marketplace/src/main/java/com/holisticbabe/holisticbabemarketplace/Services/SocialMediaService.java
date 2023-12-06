package com.holisticbabe.holisticbabemarketplace.Services;

import org.springframework.http.ResponseEntity;

import com.holisticbabe.holisticbabemarketplace.Dtos.SocialMediaDto;

public interface SocialMediaService {
    SocialMediaDto getById(long id);

    SocialMediaDto getByUserId(long id);

    void save(SocialMediaDto socialMedia);

    ResponseEntity<String> update(SocialMediaDto socialMedia, long id);

}
