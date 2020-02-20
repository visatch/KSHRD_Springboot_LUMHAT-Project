package com.kshrd.service.question;

import com.kshrd.model.Instruction;
import com.kshrd.model.Question;
import com.kshrd.model.QuestionFilter;

import java.util.List;

public interface QuestionService {
    List<Instruction> findInstructionByQuizId(QuestionFilter questionFilter);
}
