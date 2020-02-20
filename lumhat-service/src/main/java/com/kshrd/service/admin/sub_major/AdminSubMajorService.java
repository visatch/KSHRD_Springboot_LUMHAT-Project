package com.kshrd.service.admin.sub_major;

import com.kshrd.model.SubMajor;
import com.kshrd.model.form.SubMajorForm;

import java.util.List;

public interface AdminSubMajorService {
    List<SubMajor> findAll();
    SubMajor findOne(int id);
    void add(SubMajorForm subMajorForm);
    void update(SubMajorForm subMajorForm);
    void delete(int id);
}
