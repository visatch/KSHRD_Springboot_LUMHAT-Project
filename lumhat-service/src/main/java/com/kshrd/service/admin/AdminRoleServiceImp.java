package com.kshrd.service.admin;

import com.kshrd.model.Role;
import com.kshrd.model.form.RoleForm;
import com.kshrd.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminRoleServiceImp implements AdminRoleService{

    private RoleRepository roleRepository;
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findRoleByUserId(int userId) {
        return null;
    }

    @Override
    public void updateRole(RoleForm roleForm) {
        roleRepository.updateRole(roleForm);
    }
}
