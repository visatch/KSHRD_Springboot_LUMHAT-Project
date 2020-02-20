package com.kshrd.repository.adminReposity;

import com.kshrd.model.Major;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMajorRepository {
    @Select("SELECT id,major FROM lh_major ORDER BY id DESC")
    @Results({@Result(property="name",column = "major")})
    List<Major> findAll();

    @Select("SELECT id,status,major FROM lh_major WHERE id = #{id}")
    @Results({@Result(property="name",column = "major")})
    Major findOne(int id);

    @Insert("INSERT INTO lh_major (status,major) VALUES ('t', #{name})")
    void add(Major major);

    @Update("UPDATE lh_major SET major = #{name} WHERE id = #{id}")
    @Results({@Result(property = "name",column = "major")})
    void update(Major major);

    @Delete("DELETE FROM lh_major WHERE id = #{id}")
    void delete(int id);


}
