package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Dtos.CourseLevelDto;
import com.holisticbabe.holisticbabemarketplace.Models.CourseLevel;
import com.holisticbabe.holisticbabemarketplace.Repositories.CourseLevelRepository;
import com.holisticbabe.holisticbabemarketplace.Services.CourseLevelService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseLevelServiceImpl implements CourseLevelService {

    private final CourseLevelRepository courseLevelRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CourseLevelDto> getAllCourseLevels() {
        return courseLevelRepository.findAll().stream().map(level -> modelMapper.map(level, CourseLevelDto.class))
                .toList();
    }

    @Override
    public void save(CourseLevel courselevel) {
        Optional<CourseLevel> level = courseLevelRepository.findByCourseLevel(courselevel.getCourseLevel());
        if (level.isPresent()) {
            throw new RuntimeException("This course level already exists");
        }
        courseLevelRepository.save(courselevel);
    }

    @Override
    public CourseLevelDto findById(long id) {
        return modelMapper.map(courseLevelRepository.findById(id).get(), CourseLevelDto.class);
    }

    @Override
    @Transactional
    public void updateCourseLevel(long id, CourseLevel courseLevel) {
        courseLevelRepository.findByCourseLevel(courseLevel.getCourseLevel())
                .filter(level -> level.getId_course_level() != id)
                .ifPresent(existingLevel -> {
                    throw new RuntimeException("This course level already exists");
                });

        CourseLevel levelToUpdate = courseLevelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Language not found"));

        // Assuming you want to update the course level name, modify as needed
        levelToUpdate.setCourseLevel(courseLevel.getCourseLevel());
    }

    @Override
    public void delete(long id) {
        courseLevelRepository.deleteById(id);
    }

}
