package com.kshrd.model;


import java.util.List;

public class Quiz {
    private int id;
    private String title;
    private int duration;
    private boolean status;
    private User user;
    private SubMajor subMajor;
    private Level level;
    private List<Instruction> instructions;

    public Quiz(int id,String title,Level level){
        this.id=id;
        this.title=title;
        this.level=level;
    }

    public Quiz(int id, String title, int duration, boolean status, User user, SubMajor subMajor, Level level, List<Instruction> instructions) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.status = status;
        this.user = user;
        this.subMajor = subMajor;
        this.level = level;
        this.instructions = instructions;
    }

    public Quiz() {
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SubMajor getSubMajor() {
        return subMajor;
    }

    public void setSubMajor(SubMajor subMajor) {
        this.subMajor = subMajor;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", status=" + status +
                ", user=" + user +
                ", subMajor=" + subMajor +
                ", level=" + level +
                ", instructions=" + instructions +
                '}';
    }
}