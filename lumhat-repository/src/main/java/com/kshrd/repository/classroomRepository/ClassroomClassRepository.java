package com.kshrd.repository.classroomRepository;

import com.kshrd.model.classroom.ClassroomClass;
import com.kshrd.repository.classroomRepository.provider.ClassroomClassProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomClassRepository {
    @SelectProvider(method = "createClass",type = ClassroomClassProvider.class)
    int createClass(ClassroomClass classroomClass,int userId);

    @SelectProvider(method = "archiveClass",type = ClassroomClassProvider.class)
    boolean archiveClass(int id);

    @SelectProvider(method = "joinClass",type = ClassroomClassProvider.class)
    boolean joinClass(@Param("userId") int userId,@Param("code") String code);

    @SelectProvider(method = "updateClass",type = ClassroomClassProvider.class)
    void updateClass(ClassroomClass classroomClass);

    @SelectProvider(method = "deleteClass",type = ClassroomClassProvider.class)
    boolean deleteClass(int id);

    @SelectProvider(method = "restoreClass",type = ClassroomClassProvider.class)
    boolean restoreClass(int id);

    @Select("SELECT * FROM lh_class WHERE code = #{code}")
    Integer findClassByCode(String code);

    @Select("SELECT lc.id, lc.name, lc.subject, lc.room, lc.code, lc.image_path, lc.status,\n" +
            "                    (SELECT count(user_id) from lh_class_detail where class_id = lc.id and lh_class_detail.status = true) as students,\n" +
            "                    (select count(lq.id) from lh_quiz lq inner join lh_topic lt on lq.topic_id = lt.id where lt.class_id = lc.id and lq.status = true) as quizs,\n" +
            "                    lc.created_date,lu.image_url,lu.first_name,lu.last_name\n" +
            "                    from lh_class lc\n" +
            "                    inner join lh_user lu on lc.user_id = lu.id\n" +
            "                    where lc.user_id = #{userId} and lc.status = 1")
    @Results({
            @Result(column = "image_path", property = "imagePath"),
            @Result(column = "students", property = "totalStudent"),
            @Result(column = "quizs", property = "totalQuiz"),
            @Result(column = "created_date", property = "createdDate")
    })
    List<ClassroomClass> findAllArchivedClass(int userId);

    @Select("select * from get_all_class_by_user_id(#{userId})")
    @Results({
            @Result(property = "totalStudent", column = "students"),
            @Result(property = "totalQuiz", column = "quizs"),
            @Result(property = "createdDate", column = "createddate"),
            @Result(property = "imagePath", column = "image"),
            @Result(property = "teacherImage", column = "imageurl"),
            @Result(property = "teacherFirstName", column = "firstname"),
            @Result(property = "teacherLastName", column = "lastname")
    })
    List<ClassroomClass> findAllTeacherClass(int userId);

    @Select("select * from get_all_enroll_class_by_user_id(#{userId})")
    @Results({
            @Result(property = "totalStudent", column = "students"),
            @Result(property = "totalQuiz", column = "quizs"),
            @Result(property = "createdDate", column = "createddate"),
            @Result(property = "teacherImage", column = "imageurl"),
            @Result(property = "imagePath", column = "image"),
            @Result(property = "teacherFirstName", column = "firstname"),
            @Result(property = "teacherLastName", column = "lastname")
    })
    List<ClassroomClass> findAllEnrollClass(int userId);

    @SelectProvider(method = "findInfoClass",type = ClassroomClassProvider.class)
    @Results({
            @Result(property = "totalStudent", column = "students"),
            @Result(property = "totalQuiz", column = "quizs"),
            @Result(property = "imagePath",column = "image"),
            @Result(property = "teacherImage", column = "imageurl"),
            @Result(property = "teacherFirstName", column = "firstname"),
            @Result(property = "teacherLastName", column = "lastname")
    })
    ClassroomClass findInfoClass(@Param("userId") int userId,@Param("classId") int classId);
    @Select("select *,(SELECT count(user_id) from lh_class_detail where class_id = lc.id and lh_class_detail.status = true) as students,\n" +
            "(select count(lq.id) from lh_quiz lq inner join lh_topic lt on lq.topic_id = lt.id where lt.class_id = lc.id and lq.status = true) as quizs " +
            "from lh_class where id = #{id}")
    @Results({
            @Result(property = "totalStudent", column = "students"),
            @Result(property = "totalQuiz", column = "quizs"),
            @Result(property = "createdDate", column = "createddate"),
            @Result(property = "teacherImage", column = "imageurl"),
            @Result(property = "teacherFirstName", column = "firstname"),
            @Result(property = "teacherLastName", column = "lastname")
    })
    List<ClassroomClass> findClassById(int id);

    @Select("select *,(SELECT count(user_id) from lh_class_detail where class_id = lc.id and lh_class_detail.status = true) as students,\n" +
            "(select count(lq.id) from lh_quiz lq inner join lh_topic lt on lq.topic_id = lt.id where lt.class_id = lc.id and lq.status = true) as quizs" +
            " from lh_class lc\n" +
            "inner join lh_quiz_record lqr on lc.id = lqr.class_id\n" +
            "inner join lh_user lu on lc.user_id = lu.id\n" +
            "where lqr.quiz_id = #{id}")
    @Results({
            @Result(property = "totalStudent", column = "students"),
            @Result(property = "totalQuiz", column = "quizs"),
            @Result(property = "createdDate", column = "createddate"),
            @Result(property = "teacherImage", column = "imageurl"),
            @Result(property = "teacherFirstName", column = "firstname"),
            @Result(property = "teacherLastName", column = "lastname")
    })
    List<ClassroomClass> findClassByQuizId(int id);

    @Select("select count(*) from lh_class where status != 2 and user_id = #{userId};")
    int countUserClass(int userId);
}
