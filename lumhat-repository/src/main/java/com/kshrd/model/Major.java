package com.kshrd.model;

import java.util.List;

public class Major {
    private int id;
    private String name;
    private List<SubMajor> subMajors;

    public Major() {
    }

    public Major(int id, String name, List<SubMajor> subMajors) {
        this.id = id;
        this.name = name;
        this.subMajors = subMajors;
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

    public List<SubMajor> getSubMajors() {
        return subMajors;
    }

    public void setSubMajors(List<SubMajor> subMajors) {
        this.subMajors = subMajors;
    }

    @Override
    public String toString() {
        return "Major{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subMajors=" + subMajors +
                '}';
    }
}
