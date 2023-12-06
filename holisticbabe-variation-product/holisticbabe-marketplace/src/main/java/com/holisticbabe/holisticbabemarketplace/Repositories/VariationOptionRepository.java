package com.holisticbabe.holisticbabemarketplace.Repositories;

import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.VariationOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VariationOptionRepository  extends JpaRepository<VariationOption,Long> {
    VariationOption findByValue(String value);
    void deleteByVariationId(Long variationId);

    @Query("SELECT vo FROM VariationOption vo WHERE vo.variation.id=?1")
    List<VariationOption> findVariationOptionByvariation(Long id);
}
