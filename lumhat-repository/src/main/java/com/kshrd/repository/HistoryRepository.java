package com.kshrd.repository;

import com.kshrd.model.History;
import com.kshrd.model.QuizRecord;
import com.kshrd.model.Quiz;
import com.kshrd.model.SubMajor;
import com.kshrd.repository.Provider.CategoryProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.security.Provider;
import java.util.List;

@Repository
public interface HistoryRepository {

    @Select("select distinct(q.sub_major_id) as submajor_id, sm.sub_major_name as submajor_name from lh_quiz q\n" +
            "inner join lh_sub_major sm on q.sub_major_id = sm.id\n" +
            "where q.id in (select (quiz_id) from lh_quiz_record where user_id = #{userId} and is_delete_student = false)")
    @Results({
            @Result(property = "subMajor.id",column = "submajor_id"),
            @Result(property = "subMajor.name",column = "submajor_name")
    })
    List<History> findHistoryByUserId(Integer userId);

    @Select("select " +
            "qr.id as qr_id, qr.record_date as qr_record_date, qr.score as qr_score, qr.duration as qr_duration, " +
            "q.id as q_id, q.title as q_title" +
            " FROM lh_quiz q INNER JOIN lh_quiz_record qr on q.id = qr.quiz_id " +
            "WHERE q.sub_major_id=#{subMajorId} and qr.user_id=#{myUserId}")
    @Results({
            @Result(property = "id",        column = "qr_id"),
            @Result(property = "date",        column = "qr_record_date"),
            @Result(property = "score",        column = "qr_score"),
            @Result(property = "duration",        column = "qr_duration"),
            @Result(property = "fullScore",        column = "q_id", one = @One(select = "countQuestionByQuizId")),
            @Result(property = "quiz.id",   column = "q_id"),
            @Result(property = "quiz.title",column = "q_title")
    })
    List<QuizRecord> findQuizRecordByQuizId(@Param("subMajorId") Integer subMajorId, @Param("myUserId") Integer myUserId);

    @Select("select Count(ques.id) from lh_question ques " +
            "inner join lh_instruction i on ques.instruction_id = i.id " +
            "inner join lh_quiz q on i.quiz_id = q.id where q.id = #{quizId};")
    Integer countQuestionByQuizId(Integer quizId);
}
