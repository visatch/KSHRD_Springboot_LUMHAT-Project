package com.kshrd.service.history;

import com.kshrd.model.History;
import com.kshrd.model.QuizRecord;
import com.kshrd.model.Quiz;
import com.kshrd.model.SubMajor;

import java.util.List;

public interface HistoryService {
    List<History> findHistoryByUserId(Integer userId);

}
