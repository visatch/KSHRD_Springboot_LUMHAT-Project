package com.kshrd.repository;
import com.kshrd.model.Role;
import com.kshrd.model.UserRole;
import com.kshrd.model.form.RoleForm;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface RoleRepository {
    @Select("Select  r.id, r.role from lh_user_role ur INNER JOIN lh_role r ON ur.role_id=r.id where ur.user_id=#{id}")
    @Results(value = {
            @Result(property = "role", column = "role")
    })
    public List<Role> findRoleByUserId(int userId);

    @Update("UPDATE lh_user_role SET role_id=#{roleId} WHERE user_id=#{userId}")
    @Results({
            @Result(property = "roleId",column = "role_id"),
            @Result(property = "userId",column = "user_id")
    })
    public void updateRole(RoleForm roleForm);
}
