package com.kshrd.repository.classroomRepository;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.classroom.ClassroomClass;
import com.kshrd.model.classroom.ClassroomHistoryTeacher;
import com.kshrd.model.classroom.ClassroomResult;
import com.kshrd.repository.classroomRepository.provider.ClassroomHistoryTeacherProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomHistoryTeacherRepository {

    @Select("select * from (select distinct on (lqr.quiz_id) lqr.id,lqr.record_date, lqr.quiz_id, lqr.status,lq.title as quiz_title,\n" +
            "        (select lt.topic from lh_quiz lq inner join lh_topic lt on lq.topic_id = lt.id where lq.id = lqr.quiz_id),\n" +
            "        (select count(user_id) from lh_quiz_record where quiz_id = lqr.quiz_id and status = 0) as turn_in,lq.created_date date_create,\n" +
            "        lc.name as class_name\n" +
            "        from lh_quiz_record lqr inner join lh_quiz lq on lqr.quiz_id = lq.id\n" +
            "        inner join lh_class lc on lqr.class_id = lc.id\n" +
            "        where lq.status = true and lq.draft = false and class_id = ${classId} and lc.status = 0 and lqr.is_delete_teacher = false)\n" +
            "        lqr order by lqr.date_create desc;")
    @Results({
            @Result(property = "recordDate",column = "record_date"),
            @Result(property = "quizId",column = "quiz_id"),
            @Result(property = "quizTitle", column = "quiz_title"),
            @Result(property = "className", column = "class_name"),
            @Result(property = "turnIn", column = "turn_in")
    })
    List<ClassroomHistoryTeacher> filterHistoryTeacherByClassId(@Param("classId")int classId);

    @Select("select lq.id, lqr.record_date, lqr.score, lqr.user_id,\n" +
            "lu.first_name, lu.last_name\n" +
            "from lh_quiz_record lqr\n" +
            "inner join lh_user lu on lqr.user_id = lu.id\n" +
            "inner join lh_quiz lq on lqr.quiz_id = lq.id\n" +
            "where lqr.quiz_id = #{quizId} and (lqr.status = 0 or (lq.date_expire<now())) and lqr.is_delete_teacher = false order by score desc,lqr.duration asc;")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "recordDate",column = "record_date"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "fullScore",  column = "id", one = @One(select = "countQuestionByQuizId")),
    })
    List<ClassroomResult> findResultByQuizId(@Param("quizId") int quizId);

    @Select("select * from (select distinct on (lqr.quiz_id) lqr.id,lqr.record_date, lqr.quiz_id, lqr.status,lq.title as quiz_title,\n" +
            "            (select lt.topic from lh_quiz lq inner join lh_topic lt on lq.topic_id = lt.id where lq.id = lqr.quiz_id),\n" +
            "            (select count(user_id) from lh_quiz_record where quiz_id = lqr.quiz_id and status = 0) as turn_in,lq.created_date date_create\n" +
            "            from lh_quiz_record lqr inner join lh_quiz lq on lqr.quiz_id = lq.id\n" +
            "            where lq.status = true and lq.draft = false and lqr.class_id = #{classId} and lqr.is_delete_teacher = false)\n" +
            "            lqr order by lqr.date_create desc;")
    @Results({
            @Result(property = "recordDate",column = "record_date"),
            @Result(property = "quizId",column = "quiz_id"),
            @Result(property = "quizTitle", column = "quiz_title"),
            @Result(property = "turnIn", column = "turn_in")
    })
    List<ClassroomHistoryTeacher> filterAllHistoryTeacherByClassId(int classId);

    @Update("update lh_quiz_record set is_delete_teacher = true where class_id = #{classId}")
    void clearHistory(@Param("classId") int classId);

    @Select("select * from (select distinct on (lqr.quiz_id) lqr.id,lqr.record_date, lqr.quiz_id, lqr.status,lq.title as quiz_title,\n" +
            "        (select lt.topic from lh_quiz lq inner join lh_topic lt on lq.topic_id = lt.id where lq.id = lqr.quiz_id),\n" +
            "        (select count(user_id) from lh_quiz_record where quiz_id = lqr.quiz_id and status = 0) as turn_in,lq.created_date date_create,\n" +
            "        lc.name as class_name\n" +
            "        from lh_quiz_record lqr inner join lh_quiz lq on lqr.quiz_id = lq.id\n" +
            "        inner join lh_class lc on lqr.class_id = lc.id\n" +
            "        where lq.status = true and lq.draft = false and class_id != 0 and lc.status = 0 and lc.user_id = ${userId} and lqr.is_delete_teacher = false)\n" +
            "        lqr order by lqr.date_create desc;")
    @Results({
            @Result(property = "recordDate",column = "record_date"),
            @Result(property = "quizId",column = "quiz_id"),
            @Result(property = "quizTitle", column = "quiz_title"),
            @Result(property = "className", column = "class_name"),
            @Result(property = "turnIn", column = "turn_in")
    })
    List<ClassroomHistoryTeacher> filterAllHistoryTeacher(@Param("userId") int userId);

    @Select("select * from (select distinct on (lqr.quiz_id) lqr.id,lqr.record_date, lqr.quiz_id, lqr.status,lq.title as quiz_title,\n" +
            "        (select lt.topic from lh_quiz lq inner join lh_topic lt on lq.topic_id = lt.id where lq.id = lqr.quiz_id),\n" +
            "        (select count(user_id) from lh_quiz_record where quiz_id = lqr.quiz_id and (status = 0 or (lq.date_expire<now()))) as turn_in,lq.created_date date_create,\n" +
            "        lc.name as class_name\n" +
            "        from lh_quiz_record lqr inner join lh_quiz lq on lqr.quiz_id = lq.id\n" +
            "        inner join lh_class lc on lqr.class_id = lc.id\n" +
            "        where lq.draft = false and lq.status = true and class_id != 0 and lc.status = 0 and lc.user_id = ${userId} and lqr.is_delete_teacher = false)\n" +
            "        lqr order by lqr.date_create desc\n" +
            "        limit #{paging.limit} offset #{paging.offset};")
    @Results({
            @Result(property = "recordDate",column = "record_date"),
            @Result(property = "quizId",column = "quiz_id"),
            @Result(property = "quizTitle", column = "quiz_title"),
            @Result(property = "className", column = "class_name"),
            @Result(property = "turnIn", column = "turn_in")
    })
    List<ClassroomHistoryTeacher> filterAllHistoryTeacherByPage(@Param("userId") int userId, @Param("paging")Paging paging);

    @Select("select * from get_all_class_by_user_id(#{userId})")
    List<ClassroomClass> findAllTeacherClassByUserId(@Param("userId") int userId);


    @Select("select * from (select distinct on (lqr.quiz_id) lqr.id,lqr.record_date, lqr.quiz_id, lqr.status,lq.title as quiz_title,\n" +
            "        (select lt.topic from lh_quiz lq inner join lh_topic lt on lq.topic_id = lt.id where lq.id = lqr.quiz_id),\n" +
            "        (select count(user_id) from lh_quiz_record where quiz_id = lqr.quiz_id and( status = 0 or (lq.date_expire<now()) )) as turn_in,lq.created_date date_create,\n" +
            "        lc.name as class_name\n" +
            "        from lh_quiz_record lqr inner join lh_quiz lq on lqr.quiz_id = lq.id\n" +
            "        inner join lh_class lc on lqr.class_id = lc.id\n" +
            "        where lq.status = true and lq.draft = false and class_id = ${classId} and lc.status = 0 and lqr.is_delete_teacher = false)\n" +
            "        lqr order by lqr.date_create desc\n"+
            "        limit #{paging.limit} offset #{paging.offset};")
    @Results({
            @Result(property = "recordDate",column = "record_date"),
            @Result(property = "quizId",column = "quiz_id"),
            @Result(property = "quizTitle", column = "quiz_title"),
            @Result(property = "className", column = "class_name"),
            @Result(property = "turnIn", column = "turn_in")
    })
    List<ClassroomHistoryTeacher> filterHistoryTeacherByClassIdByPage(@Param("classId") int classId,@Param("paging")Paging paging);

    @Select("SELECT lc.id, lc.name, lc.subject, lc.room, lc.code, lc.image_path, lc.status,\n" +
            "                               (SELECT count(user_id) from lh_class_detail where class_id = lc.id and lh_class_detail.status = true) as students,\n" +
            "                                (select count(lq.id) from lh_quiz lq inner join lh_topic lt on lq.topic_id = lt.id where lt.class_id = lc.id and lq.status = true) as quizs,\n" +
            "                                lc.created_date,lu.image_url,lu.first_name,lu.last_name\n" +
            "                                from lh_class lc\n" +
            "                                inner join lh_user lu on lc.user_id = lu.id\n" +
            "                                inner join lh_topic l on lc.id = l.class_id\n" +
            "                                inner join lh_quiz q on l.id = q.topic_id\n" +
            "                                where q.id = #{quizId};")
    @Results({
            @Result(property = "totalStudent", column = "students"),
            @Result(property = "totalQuiz", column = "quizs"),
            @Result(property = "createdDate", column = "created_date"),
            @Result(property = "teacherImage", column = "image_url"),
            @Result(property = "imagePath", column = "image_path"),
            @Result(property = "teacherFirstName", column = "first_name"),
            @Result(property = "teacherLastName", column = "last_name")
    })
    ClassroomClass findClassInfoByQuizId(@Param("quizId") int quizId);

    @Select("select Count(ques.id) from lh_question ques\n" +
            "            inner join lh_instruction i on ques.instruction_id = i.id\n" +
            "            inner join lh_quiz q on i.quiz_id = q.id where q.id = #{quizId};")
    Integer countQuestionByQuizId(@Param("quizId") Integer quizId);

}
