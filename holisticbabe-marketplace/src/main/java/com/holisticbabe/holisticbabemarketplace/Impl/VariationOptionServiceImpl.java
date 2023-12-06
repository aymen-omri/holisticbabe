package com.holisticbabe.holisticbabemarketplace.Impl;

import com.holisticbabe.holisticbabemarketplace.Models.VariationOption;
import com.holisticbabe.holisticbabemarketplace.Repositories.VariationOptionRepository;
import com.holisticbabe.holisticbabemarketplace.Services.VariationOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariationOptionServiceImpl implements VariationOptionService {

        @Autowired
        private VariationOptionRepository variationOptionRepository;

        public List<VariationOption> getAllVariationOptions() {
            return variationOptionRepository.findAll();
        }

        public VariationOption createVariationOption(VariationOption variationOption) {
            return variationOptionRepository.save(variationOption);
        }

        public VariationOption updateVariationOption(Long id, VariationOption variationOption) {
            if (variationOptionRepository.existsById(id)) {
                variationOption.setId(id);
                return variationOptionRepository.save(variationOption);
            }
            return null;        }

        public void deleteVariationOption(Long id) {
            variationOptionRepository.deleteById(id);
        }

        public VariationOption getVariationOptionById(Long id) {
            return variationOptionRepository.findById(id).orElse(null);
        }
    public List <VariationOption> getVariationOptionByVariation(Long id) {
        return variationOptionRepository.findVariationOptionByvariation(id);
    }

    }




