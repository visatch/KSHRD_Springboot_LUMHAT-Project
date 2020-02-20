package com.kshrd.service.classroom.student;

import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.classroom.ClassroomHistoryStudent;
import com.kshrd.model.classroom.ClassroomQuiz;
import com.kshrd.model.classroom.ClassroomQuizTopic;
import com.kshrd.model.classroom.Student;
import com.kshrd.repository.classroomRepository.ClassroomQuizRepository;
import com.kshrd.repository.classroomRepository.StudentRepository;
import com.kshrd.service.classroom.quiz.ClassroomQuizService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImp implements StudentService {

    private StudentRepository studentRepository;
    private ClassroomQuizService classroomQuizService;
    private ClassroomQuizRepository classroomQuizRepository;

    @Autowired
    public void setClassroomQuizRepository(ClassroomQuizRepository classroomQuizRepository) {
        this.classroomQuizRepository = classroomQuizRepository;
    }

    @Autowired
    public StudentServiceImp(ClassroomQuizService classroomQuizService) {
        this.classroomQuizService = classroomQuizService;
    }

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student studentInfo(int user_id) {
        return studentRepository.studentInfo(user_id);
    }

    @Override
    public List<ClassroomHistoryStudent> classroomHistoryStudentInfo(int userId, int classId) {
        return studentRepository.classroomHistoryStudentInfo(userId,classId);
    }

    @Override
    public Boolean checkStudentQuizStatus(int userId, int quizId) {
        return studentRepository.checkStudentQuizStatus(userId,quizId);
    }

    @Override
    public Boolean checkStatusClassroom(@Param("classId") int classId, @Param("userId") int userId ) {
        return studentRepository.checkStatusClassroom(classId,userId);
    }

    @Override
    public Boolean checkOldUserinClass(int userId, int classId) {
        return studentRepository.checkOldUserinClass(userId,classId);
    }

    @Override
    public Boolean checkExistingQuizOldUser(Integer userId, Integer classId, Integer quizId) {
        return studentRepository.checkExistingQuizOldUser(userId,classId,quizId);
    }


    @Override
    public List<ClassroomHistoryStudent> classroomHistoryStudentInfoByPage(Paging paging, int userId, int classId) {
        return studentRepository.classroomHistoryStudentInfoByPage(paging,userId,classId);
    }

    @Override
    public void clearHistory(int classId, int userId) {
        studentRepository.clearHistory(classId,userId);
    }

    @Override
    public Boolean leaveClass(int userId, int classId) {
        return studentRepository.leaveClass(userId,classId);
    }

    @Override
    public void insertExistingQuizNewUser(Integer userId, Integer classId) {
        List<ClassroomQuizTopic> classroomQuizTopicList = classroomQuizService.findAllQuizByTopicId(classId);
        for (ClassroomQuizTopic allQuiz:classroomQuizTopicList) {
            if(allQuiz.getQuizzes()!=null){
                List<ClassroomQuiz> quizzes = allQuiz.getQuizzes();
                for (ClassroomQuiz quiz:quizzes) {
                    if(!quiz.isDraft){
                        classroomQuizRepository.insertQuizRecord(userId,quiz.getId(),classId);
                    }
                }
            }
        }
    }


}
