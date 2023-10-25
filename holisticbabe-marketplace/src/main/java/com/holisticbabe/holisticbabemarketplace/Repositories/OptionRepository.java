package com.holisticbabe.holisticbabemarketplace.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.holisticbabe.holisticbabemarketplace.Models._Option;

public interface OptionRepository extends JpaRepository<_Option, Long> {

    @Query("select o from _Option o where o.question.id_question = ?1")
    List<_Option> getOptionsByQuestionId(long id);
}
