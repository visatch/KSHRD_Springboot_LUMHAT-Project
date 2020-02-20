package com.kshrd.service.admin.major;

import com.kshrd.repository.adminReposity.AdminMajorRepository;
import com.kshrd.model.Major;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminMajorServiceImp implements AdminMajorService {

    private AdminMajorRepository adminMajorRepo;
    @Autowired
    public void setAdminMajorRepo(AdminMajorRepository adminMajorRepo) {
        this.adminMajorRepo = adminMajorRepo;
    }

    @Override
    public List<Major> findAll() {
        return adminMajorRepo.findAll();
    }

    @Override
    public Major findOne(int id) {
        return adminMajorRepo.findOne(id);
    }

    @Override
    public void add(Major major) {
        adminMajorRepo.add(major);
    }

    @Override
    public void update(Major major) {
        adminMajorRepo.update(major);
    }

    @Override
    public void delete(int id) {
        adminMajorRepo.delete(id);
    }
}
