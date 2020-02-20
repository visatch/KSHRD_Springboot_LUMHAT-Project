package com.kshrd.model.classroom;

import com.kshrd.model.Answer;
import com.kshrd.model.classroom.Instruction;

import java.util.List;

public class ClassroomQuestion {
    private int id;
    private String title;
    private Instruction instruction;
    private List<ClassroomAnswer> answers;

    public ClassroomQuestion(int id, String title, Instruction instruction, List<ClassroomAnswer> answers) {
        this.id = id;
        this.title = title;
        this.instruction = instruction;
        this.answers = answers;
    }

    public ClassroomQuestion() {
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

    public List<ClassroomAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<ClassroomAnswer> answers) {
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
