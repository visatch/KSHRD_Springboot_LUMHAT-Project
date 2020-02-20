package com.kshrd.service.quiz;

import com.kshrd.model.Level;
import com.kshrd.model.Quiz;
import com.kshrd.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImp implements QuizService {
    private QuizRepository quizRepository;

    @Autowired
    public void setQuizRepository(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public List<Level> findLevelByMajorId(Integer id) {
        return quizRepository.findLevelBySubMajorId(id);
    }

    @Override
    public List<Quiz> findAllQuiz() {
        return quizRepository.findAllQuiz();
    }

    @Override
    public List<Quiz> findQuizById(Integer id) {
        return quizRepository.findQuizById(id);
    }
}
