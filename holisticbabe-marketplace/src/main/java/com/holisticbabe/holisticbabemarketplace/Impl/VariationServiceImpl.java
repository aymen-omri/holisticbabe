package com.holisticbabe.holisticbabemarketplace.Impl;

import com.holisticbabe.holisticbabemarketplace.Models.Variation;
import com.holisticbabe.holisticbabemarketplace.Repositories.VariationOptionRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.VariationRepository;
import com.holisticbabe.holisticbabemarketplace.Services.VariationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariationServiceImpl  implements VariationService {
    @Autowired
      private VariationRepository variationRepository;

    @Autowired
    private VariationOptionRepository variationOptionRepository;
    public List<Variation> getAllVariations() {
        return variationRepository.findAll();
    }

    public Variation getVariationById(Long id) {
        return variationRepository.findById(id).orElse(null);
    }

    public Variation createVariation(Variation variation) {
        return variationRepository.save(variation);
    }

    public Variation updateVariation(Long id, Variation variation) {
        if (variationRepository.existsById(id)) {
            variation.setId(id);
            return variationRepository.save(variation);
        }
        return null;
    }

    public void deleteVariation(Long id) {
        variationRepository.deleteById(id);
    }

    @Transactional
    public void deleteVariationAndOptions(Long variationId) {
        variationOptionRepository.deleteByVariationId(variationId);
        variationRepository.deleteById(variationId);
    }
}

