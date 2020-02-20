package com.kshrd.service.admin.quiz;

import com.kshrd.model.Answer;
import com.kshrd.model.Instruction;
import com.kshrd.model.Question;
import com.kshrd.model.Quiz;

public interface AdminQuizService {

    void insertQuiz(Quiz quiz);
    void insertInstruction(Instruction instruction);
    void insertQuestion(Question question);
    void insertAnswer(Answer answer);
    Quiz findQuizById(Integer id);
    Boolean insertAllDataIntoDatabase(Quiz quiz);
    Boolean deleteQuestionByQuestionID(Integer id);
    Boolean deleteQuizByID(Integer id);
    Boolean deleteAnswerByQuestionID(Integer id);
    Boolean updateQuestionByID(Question question);
}
