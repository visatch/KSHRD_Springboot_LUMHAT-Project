package com.kshrd.model.classroom;

import java.util.List;

public class ClassroomQuizTopic {
    private String topic;
    private List<ClassroomQuiz> quizzes;

    public ClassroomQuizTopic() {
    }

    public ClassroomQuizTopic(String topic, List<ClassroomQuiz> quizzes) {
        this.topic = topic;
        this.quizzes = quizzes;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<ClassroomQuiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<ClassroomQuiz> quizzes) {
        this.quizzes = quizzes;
    }

    @Override
    public String toString() {
        return "ClassroomQuizTopic{" +
                "topic='" + topic + '\'' +
                ", quizzes=" + quizzes +
                '}';
    }
}
