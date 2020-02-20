package com.kshrd.repository.classroomRepository.provider;

import com.kshrd.model.QuestionFilter;
import com.kshrd.model.classroom.ClassroomQuiz;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class ClassroomQuestionProvider {

    public String findInstructionByQuizId(QuestionFilter questionFilter,Integer classId){
        return new SQL(){{
            SELECT("i.id as inst_id, i.title as inst_title, " +
                    "q.title as quiz_title, q.duration as quiz_duration, q.id as quiz_id");
            FROM("lh_instruction i");
            RIGHT_OUTER_JOIN("lh_quiz q ON i.quiz_id = q.id");
            INNER_JOIN("lh_topic lt on q.topic_id = lt.id");
            WHERE("q.id=" + questionFilter.getQuiz_id() + " and lt.class_id ="+classId);
            ORDER_BY("q.id");
        }}.toString();
    }

    public String findQuestionByInstructionId(Integer inst_id){
        return new SQL(){{
            SELECT("q.id as q_id, q.title as q_title, i.id as in_id");
            FROM("lh_question q");
            INNER_JOIN("lh_instruction i ON q.instruction_id = i.id");
            WHERE("i.id = " + inst_id);
        }}.toString();
    }

    public String findQuizByTopic(Integer topicId, Integer userId, Integer classId){
        return new SQL(){{
            SELECT("*");
            FROM("get_quiz_by_topic("+topicId+","+userId+","+classId+");");
        }}.toString();
    }

    public String insertQuizRecord(int userId,int quizId, int classId){
        return "insert into lh_quiz_record (record_date, score, duration, user_id, quiz_id, class_id, status) \n" +
                "values (now(),0,0,"+userId+","+quizId+","+classId+",1);";
    }

    public String findQuizById(@Param("isDraft") Boolean isDraft, @Param("quizId") int quizId){
        return new SQL(){{
            SELECT("lq.id,lq.title, duration, status, created_date, draft, topic_id, date_expire, lt.topic");
            FROM("lh_quiz lq");
            INNER_JOIN("lh_topic lt on lq.topic_id = lt.id ");
            if(isDraft!=null&&isDraft){
                WHERE("lq.status = true and lq.draft = #{isDraft} and lq.id = #{quizId}");
            }else {
                WHERE("lq.status = true and lq.id = #{quizId}");
            }
        }}.toString();
    }

}
