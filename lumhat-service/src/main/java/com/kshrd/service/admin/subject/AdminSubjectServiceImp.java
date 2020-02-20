package com.kshrd.service.admin.subject;

import com.kshrd.model.Subject;
import com.kshrd.model.form.SubjectForm;
import com.kshrd.repository.adminReposity.AdminSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminSubjectServiceImp implements AdminSubjectService {
    private AdminSubjectRepository adminSubjectRepository;

    @Autowired
    public void setAdminSubjectRepository(AdminSubjectRepository adminSubjectRepository) {
        this.adminSubjectRepository = adminSubjectRepository;
    }

    @Override
    public List<Subject> findAllSubject() {
        return adminSubjectRepository.findAllSubject();
    }

    @Override
    public void delete(int id) {
        adminSubjectRepository.delete(id);
    }


    @Override
    public void add(SubjectForm subjectForm) {
         adminSubjectRepository.add(subjectForm);
    }

    @Override
    public void update(SubjectForm subjectForm) {
        adminSubjectRepository.update(subjectForm);
    }


}
