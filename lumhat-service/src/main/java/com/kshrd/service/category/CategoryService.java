package com.kshrd.service.category;

import com.kshrd.model.Major;
import com.kshrd.model.SubMajor;
import com.kshrd.model.Subject;

import java.util.List;

public interface CategoryService {
    List<Major> findAllMajor();
    List<SubMajor> findAllSubMajor();
    List<SubMajor> findAllSubject();
    Major findMajorById(int id);
    List<Subject> findSubjectsByMajorId(int id);
}
