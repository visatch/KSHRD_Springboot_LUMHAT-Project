package com.kshrd.service.admin.major;

import com.kshrd.model.Major;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AdminMajorService {
    List<Major> findAll();
    Major findOne(int id);
    void add(Major major);
    void update(Major major);
    void delete(int id);
}
