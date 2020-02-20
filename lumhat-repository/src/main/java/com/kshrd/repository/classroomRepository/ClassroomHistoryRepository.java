package com.kshrd.repository.classroomRepository;

import com.kshrd.model.classroom.ClassroomHistoryTeacher;
import com.kshrd.model.classroom.History;
import com.kshrd.model.classroom.QuizRecord;
import com.kshrd.repository.classroomRepository.provider.ClassroomQuestionProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ClassroomHistoryRepository {

    @Select("select * from get_topic_by_class_id(#{classId});")
    @Results({
            @Result(property = "topic.id", column = "id"),
            @Result(property = "topic.topic", column = "topic")
    })
    List<History> findHistoryByClass(Integer classId);


    @SelectProvider(method = "findQuizByTopic", type = ClassroomQuestionProvider.class)
    @Results({
            @Result(property = "id", column = "qr_id"),
            @Result(property = "quiz.id", column = "q_id"),
            @Result(property = "quiz.title", column = "title"),
            @Result(property = "date", column = "created_date"),
            @Result(property = "score", column = "score"),
            @Result(property = "duration", column = "duration"),
            @Result(property = "fullScore", column = "fullscore"),
            @Result(property = "status", column = "status")
    })
    List<QuizRecord> findQuizByTopic(Integer topicId, Integer userId, Integer classId);

    @Select("select * from get_active_quiz()")
    Object getActive();
}
