package com.holisticbabe.holisticbabemarketplace.Impl;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Dtos.LessonDto;
import com.holisticbabe.holisticbabemarketplace.Models.Lesson;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Models._Subject;
import com.holisticbabe.holisticbabemarketplace.Repositories.LessonRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.MultimediaRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.SubjectRepository;
import com.holisticbabe.holisticbabemarketplace.Services.LessonService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final SubjectRepository subjectRepository;
    private final MultimediaRepository multimediaRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<LessonDto> getModuleLessons(long id) {
        return lessonRepository.getLessonsBySubjectId(id).stream()
                .map(lesson -> modelMapper.map(lesson, LessonDto.class)).toList();
    }

    @Override
    public LessonDto getLessonById(long id) {
        return modelMapper.map(lessonRepository.findById(id).get(), LessonDto.class);
    }

    @Override
    public LessonDto addLesson(Lesson lesson, long id_subject) {
        _Subject subject = subjectRepository.findById(id_subject)
                .orElseThrow(() -> new RuntimeException("Could not find subject with the given ID"));
        lesson.setSubject(subject);
        lesson.setCreatedAt(LocalDate.now());
        lesson.setStatus(1); // active
        Lesson savedLesson = lessonRepository.save(lesson);
        return modelMapper.map(savedLesson, LessonDto.class);
    }

    @Override
    public void deleteLesson(long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateLesson(long id, Lesson lesson) {
        Lesson lessonToUpdate = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find lesson with the given ID"));
        lessonToUpdate.setLessonName(lesson.getLessonName());
        lessonToUpdate.setDescription(lesson.getDescription());
    }

    @Override
    public void addLessonMultimediaContent(long id, Multimedia multimedia) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find lesson with the given ID"));
        multimedia.setLesson(lesson);
        multimediaRepository.save(multimedia);
    }

}
