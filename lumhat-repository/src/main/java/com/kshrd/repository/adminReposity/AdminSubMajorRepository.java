package com.kshrd.repository.adminReposity;

import com.kshrd.model.SubMajor;
import com.kshrd.model.form.SubMajorForm;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminSubMajorRepository {

    @Select("SELECT id,sub_major_name,parent_id FROM lh_sub_major ORDER BY id DESC")
    @Results({@Result(property = "name",column = "sub_major_name")})
    List<SubMajor> findAll();

    SubMajor findOne(int id);

    @Insert("INSERT INTO lh_sub_major (sub_major_name, major_id) VALUES (#{sub_major_name},#{major_id})")
    void add(SubMajorForm subMajorForm);

    @Update("UPDATE lh_sub_major SET sub_major_name = #{sub_major_name}, major_id = #{major_id} WHERE id = #{id}")
    void update(SubMajorForm subMajorForm);

    @Delete("DELETE FROM lh_sub_major WHERE id = #{id}")
    void delete(int id);
}
