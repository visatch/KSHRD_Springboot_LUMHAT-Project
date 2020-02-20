package com.kshrd.model.classroom;

public class Student {
    private int id;
    private String classname;
    private String subject;
    private String room;
    private String code;
    private String image;
    private int status;
    private long students;
    private long quizs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getStudents() {
        return students;
    }

    public void setStudents(long students) {
        this.students = students;
    }

    public long getQuizs() {
        return quizs;
    }

    public void setQuizs(long quizs) {
        this.quizs = quizs;
    }

    public Student(int id, String classname, String subject, String room, String code, String image, int status, long students, long quizs) {
        this.id = id;
        this.classname = classname;
        this.subject = subject;
        this.room = room;
        this.code = code;
        this.image = image;
        this.status = status;
        this.students = students;
        this.quizs = quizs;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", classname='" + classname + '\'' +
                ", subject='" + subject + '\'' +
                ", room='" + room + '\'' +
                ", code='" + code + '\'' +
                ", image='" + image + '\'' +
                ", status=" + status +
                ", students=" + students +
                ", quizs=" + quizs +
                '}';
    }
}
