package com.kshrd.model.classroom;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class ClassroomHistoryStudent {
    private Integer id;
    private Integer duration;
    private Integer score;
    private Integer fullScore;
    private Integer status;
    private String quizTitle;
    private Timestamp recordDate;

    public ClassroomHistoryStudent() {
    }

    public ClassroomHistoryStudent(Integer id, Integer duration, Integer score, Integer fullScore, Integer status, String quizTitle, Timestamp recordDate) {
        this.id = id;
        this.duration = duration;
        this.score = score;
        this.fullScore = fullScore;
        this.status = status;
        this.quizTitle = quizTitle;
        this.recordDate = recordDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public Timestamp getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Timestamp recordDate) {
        this.recordDate = recordDate;
    }

    public Integer getFullScore() {
        return fullScore;
    }

    public void setFullScore(Integer fullScore) {
        this.fullScore = fullScore;
    }

    @Override
    public String toString() {
        return "ClassroomHistoryStudent{" +
                "id=" + id +
                ", duration=" + duration +
                ", score=" + score +
                ", fullScore=" + fullScore +
                ", status=" + status +
                ", quizTitle='" + quizTitle + '\'' +
                ", recordDate=" + recordDate +
                '}';
    }
}
