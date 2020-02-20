package com.kshrd.service.admin.subject;

import com.kshrd.model.Subject;
import com.kshrd.model.form.SubMajorForm;
import com.kshrd.model.form.SubjectForm;

import java.util.List;

public interface AdminSubjectService {
    void add(SubjectForm subjectForm);
    void update(SubjectForm subjectForm);
    List<Subject>findAllSubject();
    void delete(int id);
}
