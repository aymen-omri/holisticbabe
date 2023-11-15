package com.holisticbabe.holisticbabemarketplace.Repositories;

import com.holisticbabe.holisticbabemarketplace.Models.VariationOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariationOptionRepository  extends JpaRepository<VariationOption,Long> {
    VariationOption findByValue(String value);
}
