package com.kshrd.model.classroom;

import com.kshrd.model.Question;


import java.util.List;

public class Instruction {
    private int id;
    private String title;
    private ClassroomQuiz quiz;
    private List<ClassroomQuestion> questions;

    public Instruction() {
    }

    public Instruction(int id, String title, ClassroomQuiz quiz, List<ClassroomQuestion> questions) {
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

    public ClassroomQuiz getQuiz() {
        return quiz;
    }

    public void setQuiz(ClassroomQuiz quiz) {
        this.quiz = quiz;
    }

    public List<ClassroomQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ClassroomQuestion> questions) {
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
