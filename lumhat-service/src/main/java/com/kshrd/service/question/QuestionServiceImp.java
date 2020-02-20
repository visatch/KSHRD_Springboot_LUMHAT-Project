package com.kshrd.service.question;

import com.kshrd.model.Instruction;
import com.kshrd.model.Question;
import com.kshrd.model.QuestionFilter;
import com.kshrd.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImp implements QuestionService{

    private QuestionRepository questionRepository;

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Instruction> findInstructionByQuizId(QuestionFilter questionFilter) {
        return questionRepository.findInstructionByQuizId(questionFilter);
    }
}
