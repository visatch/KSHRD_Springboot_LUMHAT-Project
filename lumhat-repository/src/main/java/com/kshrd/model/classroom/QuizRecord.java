package com.kshrd.model.classroom;

import com.kshrd.model.SubMajor;

import java.util.Date;

public class QuizRecord {
    private int id;
    private Date date;
    private int score;
    private int fullScore;
    private int duration;
    private ClassroomQuiz quiz;
    private Topic topic;
    private Integer userId;
    private int status;

    public QuizRecord() {
    }

    public QuizRecord(int id, Date date, int score, int fullScore, int duration, ClassroomQuiz quiz, Topic topic, Integer userId, int status) {
        this.id = id;
        this.date = date;
        this.score = score;
        this.fullScore = fullScore;
        this.duration = duration;
        this.quiz = quiz;
        this.topic = topic;
        this.userId = userId;
        this.status = status;
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

    public ClassroomQuiz getQuiz() {
        return quiz;
    }

    public void setQuiz(ClassroomQuiz quiz) {
        this.quiz = quiz;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
                ", topic=" + topic +
                ", userId=" + userId +
                ", status=" + status +
                '}';
    }
}
