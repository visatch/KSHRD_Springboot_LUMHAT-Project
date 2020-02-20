package com.kshrd.model.classroom;

import com.kshrd.model.Question;

public class ClassroomAnswer {
    private int id;
    private String option;
    public boolean isCorrect;
    private ClassroomQuestion question;

    public ClassroomAnswer() {
    }

    public ClassroomAnswer(int id, String option, boolean isCorrect, ClassroomQuestion question) {
        this.id = id;
        this.option = option;
        this.isCorrect = isCorrect;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean correct) {
        isCorrect = correct;
    }

    public ClassroomQuestion getQuestion() {
        return question;
    }

    public void setQuestion(ClassroomQuestion question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", option='" + option + '\'' +
                ", isCorrect=" + isCorrect +
                ", question=" + question +
                '}';
    }
}
