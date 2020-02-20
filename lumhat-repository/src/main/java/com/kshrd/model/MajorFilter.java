package com.kshrd.model;

public class MajorFilter {
    private Integer id;
    private String major;
    private String sub_major;
    private String subject;

    public MajorFilter(){}
    public MajorFilter(String major, String sub_major, String subject) {
        this.major = major;
        this.sub_major = sub_major;
        this.subject = subject;
    }

    public Integer getId(){return id;}
    public void setId(Integer id){this.id=id;}
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSub_major() {
        return sub_major;
    }

    public void setSub_major(String sub_major) {
        this.sub_major = sub_major;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "CategoryProvider{" +
                "category='" + major + '\'' +
                ", sub_major='" + sub_major + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
