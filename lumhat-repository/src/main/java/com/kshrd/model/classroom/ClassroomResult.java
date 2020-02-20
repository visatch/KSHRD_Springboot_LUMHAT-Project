package com.kshrd.model.classroom;

import java.util.Date;

public class ClassroomResult {
    private int id;
    private Date recordDate;
    private int score;
    private int fullScore;
    private String firstName;
    private String lastName;

    public ClassroomResult() {
    }

    public ClassroomResult(int id, Date recordDate, int score, int fullScore, String firstName, String lastName) {
        this.id = id;
        this.recordDate = recordDate;
        this.score = score;
        this.fullScore = fullScore;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getFullScore() {
        return fullScore;
    }

    public void setFullScore(int fullScore) {
        this.fullScore = fullScore;
    }

    @Override
    public String toString() {
        return "ClassroomResult{" +
                "id=" + id +
                ", recordDate=" + recordDate +
                ", score=" + score +
                ", fullScore=" + fullScore +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
