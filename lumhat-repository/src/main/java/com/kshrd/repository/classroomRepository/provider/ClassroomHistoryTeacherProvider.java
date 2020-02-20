package com.kshrd.repository.classroomRepository.provider;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.classroom.ClassroomResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class ClassroomHistoryTeacherProvider {
    public String filterHistoryTeacherByClassId(Paging paging, int classId){
        return "select distinct on (lqr.quiz_id) lqr.id,lqr.record_date, lqr.quiz_id, lqr.status,lq.title as quiz_title,\n" +
                "                      (select lt.topic from lh_quiz lq inner join lh_topic lt on lq.topic_id = lt.id where lq.id = lqr.quiz_id),\n" +
                "                        (select count(user_id) from lh_quiz_record where quiz_id = lqr.quiz_id and status = 0) as turn_in \n" +
                "                        from lh_quiz_record lqr inner join lh_quiz lq on lqr.quiz_id = lq.id\n" +
                "                        where lqr.class_id = "+classId+" and lq.status = true\n" +
                "                        Limit "+paging.getLimit()+" offset "+paging.getOffset()+";";
    }

    public String findResultByQuizId(int quizId,Boolean isExpire){
        return new SQL(){{
            SELECT("lqr.id, lqr.record_date, lqr.score, lqr.user_id,lu.first_name, lu.last_name");
            FROM("lh_quiz_record lqr");
            INNER_JOIN("lh_user lu on lqr.user_id = lu.id");
            if(!isExpire){
                WHERE(" lqr.quiz_id = "+quizId);
            }else {
                WHERE(" lqr.quiz_id = "+quizId+" and lqr.status = 0");
            }
            ORDER_BY(" score desc");
        }}.toString();
    }

    public String filterHistoryTeacherByClassIdByPage(int classId,Paging paging, Boolean isExpire){
        return new SQL(){{
            SELECT("distinct on (lqr.quiz_id) lqr.id,lqr.record_date, lqr.quiz_id, lqr.status,lq.title as quiz_title," +
                    "(select lt.topic from lh_quiz lq inner join lh_topic lt on lq.topic_id = lt.id where lq.id = lqr.quiz_id)," +
                    " (select count(user_id) from lh_quiz_record where quiz_id = lqr.quiz_id and status = 0) as turn_in,lc.name as class_name");
            FROM("lh_quiz_record lqr inner join lh_quiz lq on lqr.quiz_id = lq.id");
            INNER_JOIN("lh_class lc on lqr.class_id = lc.id");
            if(!isExpire){
                WHERE("lq.status = true and class_id = "+classId);
                ORDER_BY("lqr.record_date desc limit "+paging.getLimit()+" offset "+paging.getOffset());
            }else {
                WHERE("lq.status = true and class_id = "+classId+" and lc.status = 0");
                ORDER_BY("lqr.record_date desc limit "+paging.getLimit()+" offset "+paging.getOffset());
            }

        }}.toString();
    }
}
