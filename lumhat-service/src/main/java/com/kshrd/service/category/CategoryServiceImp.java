package com.kshrd.service.category;

import com.kshrd.model.Major;
import com.kshrd.model.SubMajor;
import com.kshrd.model.Subject;
import com.kshrd.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Major> findAllMajor() {
        return categoryRepository.findAllMajor();
    }

    @Override
    public List<SubMajor> findAllSubMajor() {
        return categoryRepository.findAllSubMajor();
    }

    @Override
    public List<SubMajor> findAllSubject() {
        return categoryRepository.findAllSubject();
    }

    @Override
    public Major findMajorById(int id) {
        return categoryRepository.findMajorById(id);
    }

    @Override
    public List<Subject> findSubjectsByMajorId(int id) {
        return categoryRepository.findSubjectsByMajorId(id);
    }
}
