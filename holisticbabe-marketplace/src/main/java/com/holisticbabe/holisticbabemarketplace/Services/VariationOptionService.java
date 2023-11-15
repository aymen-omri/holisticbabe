package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Models.VariationOption;

import java.util.List;

public interface VariationOptionService {

     List<VariationOption> getAllVariationOptions();
    VariationOption createVariationOption(VariationOption variationOption);
    public VariationOption updateVariationOption(Long id, VariationOption variationOption);
    void deleteVariationOption(Long id);
    VariationOption getVariationOptionById(Long id);
}
