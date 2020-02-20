package com.kshrd.repository;

import com.kshrd.model.QuizRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRecordRepository {

    @Insert("INSERT INTO lh_quiz_record (record_date, score, duration, user_id, quiz_id) VALUES " +
            "(now(), #{score}, #{duration}, #{userId}, #{quiz.id})")
    void insert(QuizRecord quizRecord);

    @Update("update lh_quiz_record set is_delete_student = true where user_id = #{userId} and class_id isnull")
    void deleteAllRecord(Integer userId);
}
