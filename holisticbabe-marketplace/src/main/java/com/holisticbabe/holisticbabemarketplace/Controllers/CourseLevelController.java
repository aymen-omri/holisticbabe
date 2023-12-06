package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.CourseLevelDto;
import com.holisticbabe.holisticbabemarketplace.Models.CourseLevel;
import com.holisticbabe.holisticbabemarketplace.Services.CourseLevelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course-level")
public class CourseLevelController {

    private final CourseLevelService courseLevelService;

    public CourseLevelController(CourseLevelService courseLevelService) {
        this.courseLevelService = courseLevelService;
    }

    @GetMapping
    public List<CourseLevelDto> getAllCourseLevels() {
        return courseLevelService.getAllCourseLevels();
    }

    @PostMapping
    public ResponseEntity<String> saveCourseLevel(@RequestBody CourseLevel courseLevel) {
        try {
            courseLevelService.save(courseLevel);
            return ResponseEntity.ok("Course level saved successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseLevelDto> getCourseLevelById(@PathVariable long id) {
        CourseLevelDto courseLevel = courseLevelService.findById(id);
        if (courseLevel != null) {
            return ResponseEntity.ok(courseLevel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCourseLevel(@PathVariable long id, @RequestBody CourseLevel courseLevel) {
        try {
            courseLevelService.updateCourseLevel(id, courseLevel);
            return ResponseEntity.ok("Course level updated successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourseLevel(@PathVariable long id) {
        try {
            courseLevelService.delete(id);
            return ResponseEntity.ok("Course level deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
