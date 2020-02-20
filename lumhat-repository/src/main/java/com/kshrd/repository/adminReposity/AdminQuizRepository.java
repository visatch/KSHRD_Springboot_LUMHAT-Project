package com.kshrd.repository.adminReposity;

import com.kshrd.model.Answer;
import com.kshrd.model.Instruction;
import com.kshrd.model.Question;
import com.kshrd.model.Quiz;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminQuizRepository {

    @Insert("INSERT INTO lh_quiz (title,duration,status,sub_major_id,level_id,created_date) VALUES (trim(#{title}),#{duration}*60,'t',#{subMajor.id},#{level.id},now())")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insertQuiz(Quiz quiz);

    @Insert("INSERT INTO lh_instruction (title,quiz_id) VALUES (trim(#{title}),#{quiz.id})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insertInstuction(Instruction instruction);

    @Insert("INSERT INTO lh_question (title,instruction_id) VALUES (trim(#{title}),#{instruction.id})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insertQuestion(Question question);

    @Insert("INSERT INTO lh_answer (option,iscorrect,question_id) VALUES (trim(#{option}),#{isCorrect},#{question.id})")
    void insertAnswer(Answer answer);

    @Select("SELECT id,title,level_id, sub_major_id FROM lh_quiz q WHERE id=#{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "level.id",column = "level_id"),
            @Result(property = "subMajor.id",column = "sub_major_id")
    })
    Quiz findQuizById(Integer id);

    @Delete("DELETE FROM lh_question WHERE id=#{id}")
    Boolean deleteQuestionByID(Integer id);

    @Delete("DELETE FROM lh_quiz where id=#{id}")
    Boolean deleteQuizByID(Integer id);

    @Delete("DELETE FROM lh_answer WHERE question_id=#{id}")
    Boolean deleteAnswerByQuestionID(Integer id);

    @Update("UPDATE lh_question set title=#{title} where id=#{id}")
    Boolean updateQuestionByID(Question question);

}
