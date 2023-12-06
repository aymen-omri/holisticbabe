package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Models.VariationOption;
import com.holisticbabe.holisticbabemarketplace.Services.VariationOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/variation-options")
public class VariationOptionController {
    @Autowired
    private VariationOptionService variationOptionService;

    @GetMapping
    public ResponseEntity<List<VariationOption>> getAllVariationOptions() {
        List<VariationOption> variationOptions = variationOptionService.getAllVariationOptions();
        return ResponseEntity.ok(variationOptions);
    }

    @PostMapping
    public ResponseEntity<VariationOption> createVariationOption(@RequestBody VariationOption variationOption) {
        VariationOption createdVariationOption = variationOptionService.createVariationOption(variationOption);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVariationOption);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VariationOption> updateVariationOption(
            @PathVariable Long id,
            @RequestBody VariationOption variationOption) {
        VariationOption updatedVariationOption = variationOptionService.updateVariationOption(id, variationOption);
        return ResponseEntity.ok(updatedVariationOption);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariationOption(@PathVariable Long id) {
        variationOptionService.deleteVariationOption(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VariationOption> getVariationOptionById(@PathVariable Long id) {
        VariationOption variationOption = variationOptionService.getVariationOptionById(id);
        if (variationOption != null) {
            return ResponseEntity.ok(variationOption);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/variation/{id}")
    public ResponseEntity<List<VariationOption>> getAllVariationOptionsByVariation(@PathVariable Long id) {
        List<VariationOption> variationOptions = variationOptionService.getVariationOptionByVariation(id);
        return ResponseEntity.ok(variationOptions);
    }
}
