package com.kshrd.repository.classroomRepository.provider;

import com.kshrd.model.classroom.ClassroomClass;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class ClassroomClassProvider {
    // create class
    public String createClass(ClassroomClass classroomClass,int userId){
        return "SELECT * FROM create_class_room(" +
                "'"+classroomClass.getName()+"'," +
                "'"+classroomClass.getSubject()+"'," +
                "'"+classroomClass.getRoom()+"'," +
                "'"+classroomClass.getImagePath()+"'," +
                "'"+userId+"'"+
        ");";
    }

    // archive class
    public String archiveClass(@Param("id") Integer id){
        return "SELECT CASE WHEN EXISTS(" +
                "SELECT * FROM archive_class("+id+")) " +
                "THEN CAST (1 AS BIT) ELSE CAST(0 AS BIT) END";
    }

    // join class
    public String joinClass(@Param("userId") Integer userId, @Param("code") String code){
        return "SELECT CASE val WHEN 1 THEN true ELSE false END AS result FROM join_class(#{userId},#{code}) AS val;";
    }

    // update class
    public String updateClass(ClassroomClass classroomClass){
        return  "UPDATE lh_class SET " +
                "name = '"+classroomClass.getName()+"'," +
                "subject = '"+classroomClass.getSubject()+"'," +
                "room = '"+classroomClass.getRoom()+"'  " +
                "WHERE id = "+classroomClass.getId()+";";
    }

    // delete class
    public String deleteClass(@Param("id") Integer id){
        return "SELECT CASE WHEN EXISTS(" +
                "SELECT * FROM delete_class("+id+")) " +
                "THEN CAST (1 AS BIT) ELSE CAST(0 AS BIT) END";
    }

    // restore class
    public String restoreClass(@Param("id") Integer id){
        return "SELECT CASE WHEN EXISTS(" +
                "SELECT * FROM restore_class("+id+")) " +
                "THEN CAST (1 AS BIT) ELSE CAST(0 AS BIT) END";
    }

    // find all archived class
    public String findAllArchivedClass(Integer userId){
        return new SQL(){{
            SELECT("*");
            FROM("lh_class");
            WHERE("user_id=#{userId}").AND().WHERE("status=1");
        }}.toString();
    }

    public String findInfoClass(@Param("userId")int userId, @Param("classId") int classId){
        return "select * from get_all_class_by_user_id(#{userId}) where id = #{classId};";
    }
}
