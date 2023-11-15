package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Models.Variation;

import java.util.List;

public interface VariationService {
    List<Variation> getAllVariations();
    Variation getVariationById(Long id);
    Variation createVariation(Variation variation);
    Variation updateVariation(Long id, Variation variation);
    void deleteVariation(Long id);
}
