package com.kshrd.service.admin;

import com.kshrd.model.Role;
import com.kshrd.model.form.RoleForm;

import java.util.List;

public interface AdminRoleService {
    List<Role> findRoleByUserId(int userId);
    void updateRole(RoleForm roleForm);
}
