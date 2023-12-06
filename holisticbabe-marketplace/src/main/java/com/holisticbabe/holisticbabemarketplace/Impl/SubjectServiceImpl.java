package com.holisticbabe.holisticbabemarketplace.Impl;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Dtos.SubjectDto;
import com.holisticbabe.holisticbabemarketplace.Models.Course;
import com.holisticbabe.holisticbabemarketplace.Models._Subject;
import com.holisticbabe.holisticbabemarketplace.Repositories.CourseRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.SubjectRepository;
import com.holisticbabe.holisticbabemarketplace.Services.SubjectService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<SubjectDto> getSubjectsByCourseId(long id) {
        return this.subjectRepository.findByCourseId(id).stream()
                .map(subject -> modelMapper.map(subject, SubjectDto.class)).toList();
    }

    @Override
    public SubjectDto getById(long id) {
        return modelMapper.map(subjectRepository.findById(id).get(), SubjectDto.class);
    }

    @Override
    public SubjectDto addSubject(long id_course, _Subject subject) {
        Course course = courseRepository.findById(id_course)
                .orElseThrow(() -> new RuntimeException("Could not find course with the given ID"));
        subject.setCourse(course);
        subject.setCreatedAt(LocalDate.now());
        subject.setStatus(1);//active
        _Subject savedSubject = subjectRepository.save(subject);
        return modelMapper.map(savedSubject , SubjectDto.class);
    }

    @Override
    @Transactional
    public void updateSubject(long id, _Subject subject) {
        _Subject subjectToUpdate = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find subject with the given ID"));
        subjectToUpdate.setName(subject.getName());
        subjectToUpdate.setDescription(subject.getDescription());
    }

    @Override
    public void deleteSubject(long id) {
        subjectRepository.deleteById(id);
    }

}
