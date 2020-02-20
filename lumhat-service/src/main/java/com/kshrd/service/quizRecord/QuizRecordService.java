package com.kshrd.service.quizRecord;

import com.kshrd.model.QuizRecord;

public interface QuizRecordService {
    void insert(QuizRecord quizRecord);
    void deleteAllRecord(Integer userId);

}
