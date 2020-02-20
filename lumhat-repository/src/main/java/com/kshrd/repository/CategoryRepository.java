package com.kshrd.repository;

import com.kshrd.model.Major;
import com.kshrd.model.SubMajor;
import com.kshrd.model.Subject;
import com.kshrd.repository.Provider.CategoryProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository {

//    Find all Major
    @Select("SELECT id,major from lh_major where status='t'")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "name",column = "major"),
            @Result(property = "subMajors", column = "id", many = @Many(select = "findSubMajorByMajorId"))
    })
    public List<Major> findAllMajor();

// Find all SubMajor
    @Select("SELECT sm.id,sm.sub_major_name,ma.major FROM lh_sub_major sm INNER JOIN lh_major ma ON sm.major_id=ma.id WHERE parent_id IS NULL ORDER BY ma.id DESC")
    @Results({
            @Result(property = "name",column = "sub_major_name"),
            @Result(property = "major.id",column = "major_id"),
            @Result(property = "major.name",column = "major")
    })
    List<SubMajor>findAllSubMajor();

//    Find all Subject
    @Select("select id, sub_major_name, major_id from lh_sub_major where parent_id is null")
    @Results({
            @Result(property = "name", column = "sub_major_name"),
            @Result(property = "subjects", column = "major_id", many = @Many(select = "findSubjectsByMajorId"))
    })
    List<SubMajor>findAllSubject();


    @Select("select id, sub_major_name, major_id from lh_sub_major where parent_id = #{id}")
    @Results({
            @Result(property = "name", column = "sub_major_name")
    })
    public List<Subject> findSubjectsByMajorId(int id);

    @SelectProvider(method = "findMajorById", type = CategoryProvider.class)
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "major"),
            @Result(property = "subMajors", column = "id", many = @Many(select = "findSubMajorByMajorId"))
    })
    public Major findMajorById(Integer id);


    @SelectProvider(method = "findSubMajorById", type = CategoryProvider.class)
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "sub_major_name"),
            @Result(property = "major.id", column = "major_id"),
            @Result(property = "subjects", column = "id", many = @Many(select = "findSubjectBySubMajorId"))
    })
    List<SubMajor> findSubMajorByMajorId(Integer id);


    @SelectProvider(method = "findSubjectById", type = CategoryProvider.class)
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "sub_major_name"),
    })
    List<Subject> findSubjectBySubMajorId(Integer id);

    
}
