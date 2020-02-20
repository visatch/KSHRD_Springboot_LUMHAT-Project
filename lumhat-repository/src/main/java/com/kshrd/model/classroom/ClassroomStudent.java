package com.kshrd.model.classroom;

public class ClassroomStudent {
    private int id;
    private String firstName;
    private String lastName;
    private String profile;

    public ClassroomStudent() {
    }

    public ClassroomStudent(int id, String firstName, String lastName, String profile) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profile = profile;
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

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "ClassroomStudent{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }
}
