package com.kshrd.model;

import java.util.List;

public class Question {
    private int id;
    private String title;
    private Instruction instruction;
    private List<Answer> answers;

    public Question(int id, String title, Instruction instruction, List<Answer> answers) {
        this.id = id;
        this.title = title;
        this.instruction = instruction;
        this.answers = answers;
    }

    public Question() {
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

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", instruction=" + instruction +
                ", answers=" + answers +
                '}';
    }
}
