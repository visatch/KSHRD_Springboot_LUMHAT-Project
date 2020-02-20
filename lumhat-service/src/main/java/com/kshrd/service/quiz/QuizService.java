package com.kshrd.service.quiz;

import com.kshrd.model.Level;
import com.kshrd.model.Quiz;

import java.util.List;

public interface QuizService {
    List<Level>findLevelByMajorId(Integer id);
    List<Quiz> findAllQuiz();
    List<Quiz> findQuizById(Integer id);
}
