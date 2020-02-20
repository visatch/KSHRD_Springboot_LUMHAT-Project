package com.kshrd.model.classroom;

import java.util.Date;

public class ClassroomHistoryTeacher {
    private int id;
    private Date recordDate;
    private int quizId;
    private int status;
    private String quizTitle;
    private String topic;
    private String className;
    private int turnIn;

    public ClassroomHistoryTeacher() {
    }

    public ClassroomHistoryTeacher(int id, Date recordDate, int quizId, int status, String quizTitle, String topic, String className, int turnIn) {
        this.id = id;
        this.recordDate = recordDate;
        this.quizId = quizId;
        this.status = status;
        this.quizTitle = quizTitle;
        this.topic = topic;
        this.className = className;
        this.turnIn = turnIn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public int getTurnIn() {
        return turnIn;
    }

    public void setTurnIn(int turnIn) {
        this.turnIn = turnIn;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "ClassroomHistoryTeacher{" +
                "id=" + id +
                ", recordDate=" + recordDate +
                ", quizId=" + quizId +
                ", status=" + status +
                ", quizTitle='" + quizTitle + '\'' +
                ", topic='" + topic + '\'' +
                ", className='" + className + '\'' +
                ", turnIn=" + turnIn +
                '}';
    }
}
