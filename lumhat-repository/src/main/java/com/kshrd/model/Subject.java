package com.kshrd.model;

import java.util.List;

public class Subject {
    private int id;
    private String name;
    private SubMajor subMajor;
    private List<Quiz> quizList;

    public Subject() {
    }
    public Subject(String name,SubMajor subMajor){
        this.name=name;
        this.subMajor=subMajor;

    }
    public Subject(int id, String name, SubMajor subMajor, List<Quiz> quizList) {
        this.id = id;
        this.name = name;
        this.subMajor = subMajor;
        this.quizList = quizList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubMajor getSubMajor() {
        return subMajor;
    }

    public void setSubMajor(SubMajor subMajor) {
        this.subMajor = subMajor;
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subMajor=" + subMajor +
                ", quizList=" + quizList +
                '}';
    }
}
