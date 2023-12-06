package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.CourseDto;
import com.holisticbabe.holisticbabemarketplace.Dtos.MultimediaDto;
import com.holisticbabe.holisticbabemarketplace.Models.Course;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        try {
            List<CourseDto> courses = courseService.getAll();
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<CourseDto>> getUserCourses(@PathVariable long id) {
        try {
            List<CourseDto> userCourses = courseService.getUserCourses(id);
            return new ResponseEntity<>(userCourses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable long id) {
        try {
            CourseDto course = courseService.getById(id);
            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Void> addCourse(@RequestBody Course course) {
        try {
            courseService.addCourse(course);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/multimedia")
    public ResponseEntity<Void> addCourseMultimedia(@RequestBody Multimedia multimedia) {
        try {
            courseService.addCourseMultimedia(multimedia);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id_course}/language/{id_language}/course-level/{id_course_level}/category/{id_category}")
    public ResponseEntity<Void> updateCourse(
            @PathVariable long id_course,
            @PathVariable long id_language,
            @PathVariable long id_course_level,
            @PathVariable long id_category,
            @RequestBody Course course) {
        try {
            courseService.updateCourse(id_course, id_language, id_course_level, id_category, course);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/multimedia")
    public ResponseEntity<List<MultimediaDto>> getCourseMultimediaContent(@PathVariable long id) {
        try {
            List<MultimediaDto> multimediaContent = courseService.getCourseMultimediaContent(id);
            return new ResponseEntity<>(multimediaContent, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable long id) {
        try {
            courseService.deleteCourse(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approveCourse(@PathVariable long id) {
        try {
            courseService.approveCourse(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> rejectCourse(@PathVariable long id) {
        try {
            courseService.rejectCourse(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/multimedia/{id}")
    public ResponseEntity<Void> updateCourseMultimedia(@PathVariable long id, @RequestBody Multimedia multimedia) {
        try {
            courseService.updateCourseMultimedia(id, multimedia);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<Void> submitCourse(@PathVariable long id) {
        try {
            courseService.submitCourse(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
