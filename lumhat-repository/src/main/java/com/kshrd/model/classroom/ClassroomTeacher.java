package com.kshrd.model.classroom;

public class ClassroomTeacher {
    private int id;
    private String firstName;
    private String lastName;
    private String profile;
    private long totalClass;
    private long totalStudent;
    private long totalQuiz;
    private long totalQuestion;

    public ClassroomTeacher() {
    }

    public ClassroomTeacher(int id, String firstName, String lastName, String profile, long totalClass, long totalStudent, long totalQuiz, long totalQuestion) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profile = profile;
        this.totalClass = totalClass;
        this.totalStudent = totalStudent;
        this.totalQuiz = totalQuiz;
        this.totalQuestion = totalQuestion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public long getTotalClass() {
        return totalClass;
    }

    public void setTotalClass(long totalClass) {
        this.totalClass = totalClass;
    }

    public long getTotalStudent() {
        return totalStudent;
    }

    public void setTotalStudent(long totalStudent) {
        this.totalStudent = totalStudent;
    }

    public long getTotalQuiz() {
        return totalQuiz;
    }

    public void setTotalQuiz(long totalQuiz) {
        this.totalQuiz = totalQuiz;
    }

    public long getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(long totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "ClassroomTeacher{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profile='" + profile + '\'' +
                ", totalClass=" + totalClass +
                ", totalStudent=" + totalStudent +
                ", totalQuiz=" + totalQuiz +
                ", totalQuestion=" + totalQuestion +
                '}';
    }
}
