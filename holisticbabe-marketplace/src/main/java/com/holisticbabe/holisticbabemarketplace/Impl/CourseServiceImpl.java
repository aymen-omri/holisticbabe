package com.holisticbabe.holisticbabemarketplace.Impl;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Dtos.CourseDto;
import com.holisticbabe.holisticbabemarketplace.Dtos.MultimediaDto;
import com.holisticbabe.holisticbabemarketplace.Models.Category;
import com.holisticbabe.holisticbabemarketplace.Models.Course;
import com.holisticbabe.holisticbabemarketplace.Models.CourseLevel;
import com.holisticbabe.holisticbabemarketplace.Models.Language;
import com.holisticbabe.holisticbabemarketplace.Models.Lesson;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Models._Subject;
import com.holisticbabe.holisticbabemarketplace.Repositories.CategoryRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.CourseLevelRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.CourseRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.LanguageRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.LessonRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.MultimediaRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.SubjectRepository;
import com.holisticbabe.holisticbabemarketplace.Services.CourseService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final MultimediaRepository multimediaRepository;
    private final CourseLevelRepository courseLevelRepository;
    private final LanguageRepository languageRepository;
    private final CategoryRepository categoryRepository;
    private final SubjectRepository subjectRepository;
    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CourseDto> getAll() {
        return courseRepository.findAll()
                .stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .toList();
    }

    @Override
    public List<CourseDto> getUserCourses(long id) {
        return courseRepository.findCoursesByIdUser(id)
                .stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .toList();
    }

    @Override
    public CourseDto getById(long id) {
        return modelMapper.map(courseRepository.findById(id).get(), CourseDto.class);
    }

    @Override
    public void addCourse(Course course) {
        course.setCreatedAt(LocalDate.now());
        course.setStatus(0);
        courseRepository.save(course);
    }

    @Override
    public void addCourseMultimedia(Multimedia multimedia) {
        multimediaRepository.save(multimedia);
    }

    @Override
    @Transactional
    public void updateCourse(long id_course, long id_language, long id_course_level, long id_category, Course course) {
        Course courseToUpdate = courseRepository.findById(id_course).orElseThrow(
                () -> new RuntimeException("Course not found"));
        Language language = languageRepository.findById(id_language)
                .orElseThrow(() -> new RuntimeException("Language not found!"));
        CourseLevel courseLevel = courseLevelRepository.findById(id_course_level)
                .orElseThrow(() -> new RuntimeException("Course level not found!"));
        Category category = categoryRepository.findById(id_category)
                .orElseThrow(() -> new RuntimeException("Category not found!"));

        courseToUpdate.setCategory(category);
        courseToUpdate.setLevel(courseLevel);
        courseToUpdate.setLanguage(language);
        courseToUpdate.setTitle(course.getTitle());
        courseToUpdate.setSubtitle(course.getSubtitle());
        courseToUpdate.setDescription(course.getDescription());
        courseToUpdate.setRequirements(course.getRequirements());
        courseToUpdate.setTarget(course.getTarget());
        courseToUpdate.setUpdatedAt(LocalDate.now());

    }

    @Override
    public List<MultimediaDto> getCourseMultimediaContent(long id) {
        return multimediaRepository.getCourseFiles(id)
                .stream()
                .map(multi -> modelMapper.map(multi, MultimediaDto.class))
                .toList();
    }

    @Override
    @Transactional
    public void deleteCourse(long id) {
        Course courseTodelete = courseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Course not found"));
        courseTodelete.setDeletedAt(LocalDate.now());
        courseTodelete.setStatus(4);
    }

    @Override
    @Transactional
    public void approveCourse(long id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Course not found"));
        course.setStatus(2);
    }

    @Override
    @Transactional
    public void rejectCourse(long id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Course not found"));
        course.setStatus(3);
    }

    @Override
    public void updateCourseMultimedia(long id, Multimedia multimedia) {
        Multimedia content = multimediaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("File not found"));
        content.setUrl(multimedia.getUrl());
        content.setName(multimedia.getName());
        content.setType(multimedia.getType());
    }

    @Override
    @Transactional
    public void submitCourse(long id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Course not found"));
        List<_Subject> subjects = subjectRepository.findByCourseId(course.getId_course());
        if (subjects.size() < 2) {
            throw new RuntimeException("You need to add at least two subjects");
        } else {
            subjects.forEach(subject -> {
                List<Lesson> lessons = lessonRepository.getLessonsBySubjectId(subject.getId_subject());
                if (lessons.size() < 2) {
                    throw new RuntimeException("Each subject must have at least 2 lessons");
                }
            });
        }
        course.setStatus(1);
    }
}
