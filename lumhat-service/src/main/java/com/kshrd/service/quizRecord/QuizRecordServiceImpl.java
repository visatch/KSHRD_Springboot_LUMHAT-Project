package com.kshrd.service.quizRecord;

import com.kshrd.model.QuizRecord;
import com.kshrd.repository.QuizRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizRecordServiceImpl implements QuizRecordService {

    private QuizRecordRepository quizRecordRepository;

    @Autowired
    public void setQuizRecordRepository(QuizRecordRepository quizRecordRepository) {
        this.quizRecordRepository = quizRecordRepository;
    }

    @Override
    public void insert(QuizRecord quizRecord) {
        quizRecordRepository.insert(quizRecord);
    }

    @Override
    public void deleteAllRecord(Integer userId) {
        quizRecordRepository.deleteAllRecord(userId);
    }
}
