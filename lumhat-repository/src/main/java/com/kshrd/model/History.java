package com.kshrd.model;

import java.util.List;


public class History {
   private SubMajor subMajor;
   List<QuizRecord> quizRecords;

    public History() {
    }

    public History(SubMajor subMajor, List<QuizRecord> quizRecords) {
        this.subMajor = subMajor;
        this.quizRecords = quizRecords;
    }

    public SubMajor getSubMajor() {
        return subMajor;
    }

    public void setSubMajor(SubMajor subMajor) {
        this.subMajor = subMajor;
    }

    public List<QuizRecord> getQuizRecords() {
        return quizRecords;
    }

    public void setQuizRecords(List<QuizRecord> quizRecords) {
        this.quizRecords = quizRecords;
    }

    @Override
    public String toString() {
        return "History{" +
                "subMajor=" + subMajor +
                ", quizRecords=" + quizRecords +
                '}';
    }
}
