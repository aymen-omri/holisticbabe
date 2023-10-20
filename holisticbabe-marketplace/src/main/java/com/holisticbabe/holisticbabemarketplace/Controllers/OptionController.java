package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Models.Option;
import com.holisticbabe.holisticbabemarketplace.Services.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/option")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;

    @GetMapping("/question/{id}")
    public List<Option> getQuestionOptions(@PathVariable long id) {
        return optionService.getQuestionOptions(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Option> getOptionById(@PathVariable long id) {
        Option option = optionService.getOptionById(id);
        if (option != null) {
            return ResponseEntity.ok(option);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOption(@RequestBody Option option) {
        try {
            Option addedOption = optionService.addOption(option);
            return ResponseEntity.status(201).body(addedOption);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Problem while inserting option!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOption(@PathVariable long id) {
        try {
            optionService.deleteOption(id);
            return ResponseEntity.ok("Option deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateOption(@PathVariable long id, @RequestBody Option option) {
        ResponseEntity<String> response = optionService.updateOption(id, option);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("Option updated successfully!");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }
}
