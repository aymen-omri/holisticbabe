package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Models.Variation;
import com.holisticbabe.holisticbabemarketplace.Services.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/variations")
public class VariationController {
    @Autowired
    private  VariationService variationService;



    @GetMapping
    public ResponseEntity<List<Variation>> getAllVariations() {
        return ResponseEntity.ok(variationService.getAllVariations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Variation> getVariationById(@PathVariable Long id) {
        Variation variation = variationService.getVariationById(id);
        if (variation != null) {
            return ResponseEntity.ok(variation);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Variation> createVariation(@RequestBody Variation variation) {
        Variation createdVariation = variationService.createVariation(variation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVariation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Variation> updateVariation(
            @PathVariable Long id,
            @RequestBody Variation variation
    ) {
        Variation updatedVariation = variationService.updateVariation(id, variation);
        if (updatedVariation != null) {
            return ResponseEntity.ok(updatedVariation);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariation(@PathVariable Long id) {
        variationService.deleteVariation(id);
        return ResponseEntity.noContent().build();
    }
}

