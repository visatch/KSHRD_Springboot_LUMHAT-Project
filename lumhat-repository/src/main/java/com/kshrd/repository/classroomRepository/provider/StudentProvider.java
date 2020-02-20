package com.kshrd.repository.classroomRepository.provider;

import com.kshrd.model.classroom.Student;
import org.apache.ibatis.jdbc.SQL;

public class StudentProvider {
    public String getStudentInfo(Student student){
        return new SQL(){{
        }}.toString();
    }
}
