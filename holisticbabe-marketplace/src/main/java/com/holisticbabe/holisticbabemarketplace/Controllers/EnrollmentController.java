package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Models.Enrollment;
import com.holisticbabe.holisticbabemarketplace.Services.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollment")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Enrollment>> getAllEnrollmentsByUserId(@PathVariable long userId) {
        List<Enrollment> enrollments = enrollmentService.getAllByUserId(userId);
        return new ResponseEntity<>(enrollments, HttpStatus.OK);
    }

    @GetMapping("/{enrollmentId}")
    public ResponseEntity<?> getEnrollmentById(@PathVariable long enrollmentId) {
        try {
            Enrollment enrollment = enrollmentService.getById(enrollmentId);
            return new ResponseEntity<>(enrollment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create/user/{id_user}/course/{id_course}")
    public ResponseEntity<?> createEnrollment(@PathVariable("id_user") long userId,
            @PathVariable("id_course") long courseId) {
        try {
            enrollmentService.createEnrollment(userId, courseId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-finished-lesson/{id_enrollment}/lesson/{id_lesson}")
    public ResponseEntity<?> addFinishedLessons(@PathVariable("id_enrollment") long enrollmentId,
            @PathVariable("id_lesson") long lessonId) {
        try {
            enrollmentService.addFinishedLessons(enrollmentId, lessonId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
