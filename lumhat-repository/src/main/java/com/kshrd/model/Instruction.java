package com.kshrd.model;

import java.util.List;

public class Instruction {
    private int id;
    private String title;
    private Quiz quiz;
    private List<Question> questions;

    public Instruction() {
    }

    public Instruction(int id, String title, Quiz quiz, List<Question> questions) {
        this.id = id;
        this.title = title;
        this.quiz = quiz;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", quiz=" + quiz +
                ", questions=" + questions +
                '}';
    }
}
