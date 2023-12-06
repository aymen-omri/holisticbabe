package com.holisticbabe.holisticbabemarketplace.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import com.holisticbabe.holisticbabemarketplace.Dtos.SocialMediaDto;
import com.holisticbabe.holisticbabemarketplace.Models.SocialMedia;
import com.holisticbabe.holisticbabemarketplace.Repositories.SocialMediaRepository;
import com.holisticbabe.holisticbabemarketplace.Requests.SuccessMessageRequest;
import com.holisticbabe.holisticbabemarketplace.Services.SocialMediaService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocialMediaServiceImpl implements SocialMediaService {

    private final SocialMediaRepository socialMediaRepository;
    private final ModelMapper modelMapper;

    @Override
    public SocialMediaDto getById(long id) {
        Optional<SocialMedia> socialMedia = socialMediaRepository.findById(id);
        if (socialMedia.isPresent()) {
            return this.modelMapper.map(socialMedia.get(), SocialMediaDto.class);
        } else {
            return null;
        }
    }

    @Override
    public SocialMediaDto getByUserId(long id) {
        Optional<SocialMedia> socialMedia = socialMediaRepository.getSocialMediaByIdUser(id);
        if (socialMedia.isPresent()) {
            return this.modelMapper.map(socialMedia.get(), SocialMediaDto.class);
        } else {
            return null;
        }
    }

    @Override
    public void save(SocialMedia socialMedia) {
        socialMediaRepository.save(socialMedia);
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(SocialMediaDto socialMedia, long id) {
        SocialMedia existingSocialMedia = socialMediaRepository.findById(id).orElse(null);
        if (existingSocialMedia == null) {
            return ResponseEntity.notFound().build();
        }
        existingSocialMedia.setFacebookLink(socialMedia.getFacebookLink());
        existingSocialMedia.setInstagramLink(socialMedia.getInstagramLink());
        existingSocialMedia.setTiktokLink(socialMedia.getTiktokLink());
        existingSocialMedia.setLinkedinLink(socialMedia.getLinkedinLink());
        return ResponseEntity.ok(new SuccessMessageRequest(200 , "updated successfully!"));
    }

}
