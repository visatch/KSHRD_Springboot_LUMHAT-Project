package com.kshrd.service.classroom.quiz;
import com.kshrd.configuration.utility.Paging;
import com.kshrd.model.classroom.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface ClassroomQuizService {
    Boolean insertAllDataIntoDatabase(ClassroomQuiz quiz);
    void insertQuiz(ClassroomQuiz quiz);
    void insertInstruction(Instruction instruction);
    void insertQuestion(ClassroomQuestion question);
    void insertAnswer(ClassroomAnswer answer);
    List<Topic> findAllTopicByClassId(int classId);
    List<ClassroomQuizTopic> findAllQuizByTopicId(int classId);
    List<ClassroomQuizTopic> findAllQuizActiveByTopicId(int classId);
    Boolean deleteQuizByID(Integer id);
    ClassroomQuiz findQuizById(Boolean isDraft,int quizId);
    void insertQuizRecordAfterAssignQuiz(ClassroomQuiz quiz);
    List<ClassroomQuiz> findAllQuizByClassId(int classId);
    List<ClassroomQuiz> findAllQuizByClassIdByPage(int classId, Paging paging);
    List<ClassroomQuiz> findQuizStudentByClassId(@Param("classId") int classId, @Param("userId")int userId);
    List<ClassroomQuiz> findQuizStudentByClassIdByPage(@Param("classId") int classId, @Param("userId")int userId,@Param("paging")Paging paging);
    Topic findTopicByQuizId(Integer id);
    void removeQuizFromDbById(Integer id);
    void publishQuiz(@Param("quizId") Integer quizId,@Param("duration") Integer duration,@Param("dateExpire") Date dateExpire);
}
