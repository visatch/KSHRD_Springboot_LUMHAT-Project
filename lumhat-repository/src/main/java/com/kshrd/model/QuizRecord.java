package com.kshrd.model;

import java.util.Date;

public class QuizRecord {
    private int id;
    private Date date;
    private int score;
    private int fullScore;
    private int duration;
    private Quiz quiz;
    private SubMajor subMajor;
    private Integer userId;

    public QuizRecord() {
    }

    public QuizRecord(int id, Date date, int score, int fullScore, int duration, Quiz quiz, SubMajor subMajor, Integer userId) {
        this.id = id;
        this.date = date;
        this.score = score;
        this.fullScore = fullScore;
        this.duration = duration;
        this.quiz = quiz;
        this.subMajor = subMajor;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getFullScore() {
        return fullScore;
    }

    public void setFullScore(int fullScore) {
        this.fullScore = fullScore;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public SubMajor getSubMajor() {
        return subMajor;
    }

    public void setSubMajor(SubMajor subMajor) {
        this.subMajor = subMajor;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "QuizRecord{" +
                "id=" + id +
                ", date=" + date +
                ", score=" + score +
                ", fullScore=" + fullScore +
                ", duration=" + duration +
                ", quiz=" + quiz +
                ", subMajor=" + subMajor +
                ", userId=" + userId +
                '}';
    }
}
