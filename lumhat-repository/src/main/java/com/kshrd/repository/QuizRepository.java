package com.kshrd.repository;

import com.kshrd.model.Level;
import com.kshrd.model.Quiz;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository {

    @Select("SELECT DISTINCT l.id,level FROM lh_level l INNER JOIN lh_quiz q ON l.id=q.level_id WHERE q.sub_major_id=#{id} ORDER BY l.id")
    @Results({
            @Result(property = "name",column = "level")
    })
    List<Level> findLevelBySubMajorId(Integer id);

    @Select("SELECT id, title,sub_major_id,level_id FROM lh_quiz")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "title",column = "title"),
    })
    List<Quiz> findAllQuiz();

    @Select("SELECT q.id,title,level_id,l.level, sub_major_id \n" +
            "FROM lh_quiz q \n" +
            "INNER JOIN lh_level l ON q.level_id=l.id\n" +
            "WHERE sub_major_id=#{id} and q.status='t' ORDER BY id")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "level.id",column = "level_id"),
            @Result(property = "level.name",column = "level"),
            @Result(property = "subMajor.id",column = "sub_major_id")
    })
    List<Quiz> findQuizById(Integer id);
}
