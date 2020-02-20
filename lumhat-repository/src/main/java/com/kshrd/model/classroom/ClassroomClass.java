package com.kshrd.model.classroom;

import java.util.Date;

public class ClassroomClass {
    private int id;
    private String name;
    private String subject;
    private String room;
    private String code;
    private String imagePath;
    private int totalStudent;
    private int totalQuiz;
    private Date createdDate;
    private String teacherImage;
    private String teacherFirstName;
    private String teacherLastName;

    public ClassroomClass() {
    }

    public ClassroomClass(int id, String name, String subject, String room, String code, String imagePath, int totalStudent, int totalQuiz, Date createdDate, String teacherImage, String teacherFirstName, String teacherLastName) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.room = room;
        this.code = code;
        this.imagePath = imagePath;
        this.totalStudent = totalStudent;
        this.totalQuiz = totalQuiz;
        this.createdDate = createdDate;
        this.teacherImage = teacherImage;
        this.teacherFirstName = teacherFirstName;
        this.teacherLastName = teacherLastName;
    }

    public ClassroomClass(String name,String subject,String room){
        this.name = name;
        this.subject = subject;
        this.room = room;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getTotalStudent() {
        return totalStudent;
    }

    public void setTotalStudent(int totalStundent) {
        this.totalStudent = totalStundent;
    }

    public int getTotalQuiz() {
        return totalQuiz;
    }

    public void setTotalQuiz(int totalQuiz) {
        this.totalQuiz = totalQuiz;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getTeacherImage() {
        return teacherImage;
    }

    public void setTeacherImage(String teacherImage) {
        this.teacherImage = teacherImage;
    }

    public String getTeacherFirstName() {
        return teacherFirstName;
    }

    public void setTeacherFirstName(String teacherFirstName) {
        this.teacherFirstName = teacherFirstName;
    }

    public String getTeacherLastName() {
        return teacherLastName;
    }

    public void setTeacherLastName(String teacherLastName) {
        this.teacherLastName = teacherLastName;
    }

    @Override
    public String toString() {
        return "ClassroomClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", room='" + room + '\'' +
                ", code='" + code + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", totalStundent=" + totalStudent +
                ", totalQuiz=" + totalQuiz +
                ", createdDate=" + createdDate +
                ", teacherImage='" + teacherImage + '\'' +
                ", teacherFirstName='" + teacherFirstName + '\'' +
                ", teacherLastName='" + teacherLastName + '\'' +
                '}';
    }
}
