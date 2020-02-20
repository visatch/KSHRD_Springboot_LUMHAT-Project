package com.kshrd.repository.adminReposity;

import com.kshrd.model.Subject;
import com.kshrd.model.form.SubjectForm;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminSubjectRepository {


    @Insert("INSERT INTO lh_sub_major(sub_major_name,parent_id) VALUES (#{sub_major_name},#{parent_id})")
    void add(SubjectForm subjectForm);

    @Update("UPDATE lh_sub_major SET sub_major_name=#{sub_major_name}, parent_id=#{parent_id} WHERE id=#{id}")
    void update(SubjectForm subjectForm);

    @Delete("DELETE FROM lh_sub_major WHERE id=#{id}")
    void delete(int id);

    @Select("SELECT l1.id,l1.sub_major_name subject, l2.sub_major_name submajor FROM lh_sub_major l1 INNER JOIN lh_sub_major l2 on l1.parent_id=l2.id WHERE l1.parent_id IS NOT NULL ORDER BY l1.id DESC")
    @Results({
            @Result(property = "name",column = "subject"),
            @Result(property = "subMajor.name",column = "submajor")
    })
    List<Subject>findAllSubject();

}
