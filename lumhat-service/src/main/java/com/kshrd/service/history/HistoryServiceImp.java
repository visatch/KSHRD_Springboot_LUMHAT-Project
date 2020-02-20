package com.kshrd.service.history;

import com.kshrd.model.History;
import com.kshrd.model.QuizRecord;
import com.kshrd.model.Quiz;
import com.kshrd.model.SubMajor;
import com.kshrd.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableAsync
public class HistoryServiceImp implements HistoryService {

    private HistoryRepository historyRepository;

    @Autowired
    public void setHistoryRepository(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public List<History> findHistoryByUserId(Integer userId) {
        List<History> histories = historyRepository.findHistoryByUserId(userId);
        for(int i=0 ; i<histories.size() ; i++) {
            histories.get(i).setQuizRecords(historyRepository.findQuizRecordByQuizId(histories.get(i).getSubMajor().getId(), userId));
        }
        return histories;
    }
}
