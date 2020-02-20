package com.kshrd.model;

import java.util.List;

public class SubMajor {
    private int id;
    private String name;
    private Major major;
    private List<Subject> subjects;

    public SubMajor() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Major getMajor() {
        return major;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public SubMajor(int id, String name, Major major, List<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "SubMajorForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", major=" + major +
                ", subjects=" + subjects +
                '}';
    }
}
