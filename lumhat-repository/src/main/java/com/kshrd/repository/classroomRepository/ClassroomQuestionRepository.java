package com.kshrd.repository.classroomRepository;

import com.kshrd.model.Answer;
import com.kshrd.model.Instruction;
import com.kshrd.model.Question;
import com.kshrd.model.QuestionFilter;
import com.kshrd.repository.Provider.QuestionProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomQuestionRepository {
    @SelectProvider(method = "findInstructionByQuizId", type = QuestionProvider.class)
    @Results({
            @Result(property = "id", column = "inst_id"),
            @Result(property = "title", column = "inst_title"),
            @Result(property = "quiz.id",column = "quiz_id"),
            @Result(property = "quiz.title",column = "quiz_title"),
            @Result(property = "quiz.duration",column = "quiz_duration"),
            @Result(property = "questions",column = "inst_id", many = @Many(select = "findQuestionByInstructionId"))
    })
    List<Instruction> findInstructionByQuizId(QuestionFilter questionFilter);


    @SelectProvider(method = "findQuestionByInstructionId", type = QuestionProvider.class)
    @Results({
            @Result(property = "id",column = "q_id"),
            @Result(property = "title",column = "q_title"),
            @Result(property = "instruction.id", column = "in_id"),
            @Result(property = "answers", column = "q_id", many = @Many(select = "findAnswersByQuestionId")),
    })
    List<Question> findQuestionByInstructionId(Integer inst_id);


    @Select("SELECT * FROM lh_answer WHERE question_id = #{id}")
    @Results({
            @Result(property = "isCorrect", column = "iscorrect")
    })
    List<Answer> findAnswersByQuestionId(Integer id);

}
