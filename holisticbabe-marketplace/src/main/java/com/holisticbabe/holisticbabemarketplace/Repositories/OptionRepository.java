package com.holisticbabe.holisticbabemarketplace.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.holisticbabe.holisticbabemarketplace.Models.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {

    @Query("select o from Option o where o.question.id_question = ?1")
    List<Option> getOptionsByQuestionId(long id);
}
