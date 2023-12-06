package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Dtos.SubjectDto;
import com.holisticbabe.holisticbabemarketplace.Models._Subject;

public interface SubjectService {
    List<SubjectDto> getSubjectsByCourseId(long id);

    SubjectDto getById(long id);

    SubjectDto addSubject(long id_course, _Subject subject);

    void updateSubject(long id, _Subject subject);

    void deleteSubject(long id);
}
