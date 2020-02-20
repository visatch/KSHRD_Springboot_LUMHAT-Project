package com.kshrd.repository.classroomRepository;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.classroom.ClassroomStudent;
import com.kshrd.model.classroom.ClassroomTeacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ClassroomTeacherRepository {

    @Select("select * from get_student_by_class_id(#{classId})")
    @Results({
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name")
    })
    List<ClassroomStudent> findAllStudentByClassId(int classId);

    @Update("update lh_class_detail set status = false where user_id = #{userId} and class_id = #{classId}")
    void deleteStudentFromClass(@Param("userId") int userId,@Param("classId")  int classId);

    @Update("update lh_class set image_path = #{imgName} where id = #{id};")
    void updateCover(@Param("imgName") String imgName,@Param("id") int classId);

    @Select("select distinct lu.id,lu.first_name,lu.last_name,\n" +
            "(select count(id) from lh_class lc1 where lc1.status = 0 and lc1.user_id = lu.id) total_class\n" +
            "from lh_class lc\n" +
            "inner join lh_user lu on lu.id = lc.user_id\n" +
            "where lc.status = 0;")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "first_name", property = "firstName"),
            @Result(column = "last_name", property = "lastName"),
            @Result(column = "total_class",property = "totalClass"),
            @Result(property = "totalStudent", column = "id", many = @Many(select = "findAllStudentByTeacherId")),
            @Result(property = "totalQuiz", column = "id", many = @Many(select = "findAllQuizByTeacherId")),
            @Result(property = "totalQuestion", column = "id", many = @Many(select = "findAllQuestionByTeacherId"))
    })
    List<ClassroomTeacher> findTeacherInfo();

    @Select("select distinct lu.id,lu.first_name,lu.last_name,lu.image_url,\n" +
            "(select count(id) from lh_class lc1 where lc1.status = 0 and lc1.user_id = lu.id) total_class\n" +
            "from lh_class lc\n" +
            "inner join lh_user lu on lu.id = lc.user_id\n" +
            "where lc.status = 0 " +
            "limit #{paging.limit} offset #{paging.offset};;")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "first_name", property = "firstName"),
            @Result(column = "last_name", property = "lastName"),
            @Result(column = "total_class",property = "totalClass"),
            @Result(column = "image_url",property = "profile"),
            @Result(property = "totalStudent", column = "id", many = @Many(select = "findAllStudentByTeacherId")),
            @Result(property = "totalQuiz", column = "id", many = @Many(select = "findAllQuizByTeacherId")),
            @Result(property = "totalQuestion", column = "id", many = @Many(select = "findAllQuestionByTeacherId"))
    })
    List<ClassroomTeacher> findTeacherInfoByPage(@Param("paging") Paging paging);

    @Select("select count(lcd.class_id) from lh_class_detail lcd\n" +
            "inner join lh_class lc on lcd.class_id = lc.id\n" +
            "inner join lh_user lu on lc.user_id = lu.id\n" +
            "where lcd.status = true and lc.user_id = #{tcId} and lcd.status = true")
    int findAllStudentByTeacherId(int tcId);

    @Select("select count(lq.id) from lh_quiz lq\n" +
            "inner join lh_topic lt on lq.topic_id = lt.id\n" +
            "inner join lh_class lc on lt.class_id = lc.id\n" +
            "where lc.user_id = #{tcId} and lq.status = true;")
    int findAllQuizByTeacherId(int tcId);

    @Select("select count(lq.id) from lh_question lq\n" +
            "inner join lh_instruction li on lq.instruction_id = li.id\n" +
            "inner join lh_quiz l on li.quiz_id = l.id\n" +
            "inner join lh_topic lt on l.topic_id = lt.id\n" +
            "inner join lh_class lc on lt.class_id = lc.id\n" +
            "where lc.user_id = #{tcId}  and l.status = true;")
    int findAllQuestionByTeacherId(int tcId);
}
