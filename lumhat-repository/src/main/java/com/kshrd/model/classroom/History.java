package com.kshrd.model.classroom;

import java.util.List;


public class History {
   private Topic topic;
   List<com.kshrd.model.classroom.QuizRecord> quizRecords;

    public History() {
    }

    public History(Topic topic, List<QuizRecord> quizRecords) {
        this.topic = topic;
        this.quizRecords = quizRecords;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public List<com.kshrd.model.classroom.QuizRecord> getQuizRecords() {
        return quizRecords;
    }

    public void setQuizRecords(List<QuizRecord> quizRecords) {
        this.quizRecords = quizRecords;
    }

    @Override
    public String toString() {
        return "History{" +
                "topic=" + topic +
                ", quizRecords=" + quizRecords +
                '}';
    }
}
