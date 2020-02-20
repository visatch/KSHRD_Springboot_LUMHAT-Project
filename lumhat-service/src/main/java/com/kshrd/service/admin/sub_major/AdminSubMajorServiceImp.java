package com.kshrd.service.admin.sub_major;

import com.kshrd.model.SubMajor;
import com.kshrd.model.form.SubMajorForm;
import com.kshrd.repository.adminReposity.AdminSubMajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminSubMajorServiceImp implements AdminSubMajorService {

    private AdminSubMajorRepository subMajorRepository;

    @Autowired
    public void setSubMajorRepository(AdminSubMajorRepository subMajorRepository) {
        this.subMajorRepository = subMajorRepository;
    }

    @Override
    public List<SubMajor> findAll() {
        return null;
    }

    @Override
    public SubMajor findOne(int id) {
        return null;
    }

    @Override
    public void add(SubMajorForm subMajorForm) {
        subMajorRepository.add(subMajorForm);
    }

    @Override
    public void update(SubMajorForm subMajorForm) {
        subMajorRepository.update(subMajorForm);
    }


    @Override
    public void delete(int id) {
        subMajorRepository.delete(id);
    }


}
