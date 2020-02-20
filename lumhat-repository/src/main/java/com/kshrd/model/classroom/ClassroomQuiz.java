package com.kshrd.model.classroom;
import com.kshrd.model.User;

import java.util.Date;
import java.util.List;

public class ClassroomQuiz {
    private int id;
    private String title;
    private int duration;
    private Date dateExpire;
    private int hourExpire;
    private Date createdDate;
    private boolean status;
    public boolean isDraft;
    private User user;
    private int classId;
    private Topic topic;
    private List<Instruction> instructions;
    private int quizRecordStatus;

    public ClassroomQuiz() {
    }

    public ClassroomQuiz(int id, String title, int duration, Date dateExpire, int hourExpire, Date createdDate, boolean status, boolean isDraft, User user, int classId, Topic topic, List<Instruction> instructions, int quizRecordStatus) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.dateExpire = dateExpire;
        this.hourExpire = hourExpire;
        this.createdDate = createdDate;
        this.status = status;
        this.isDraft = isDraft;
        this.user = user;
        this.classId = classId;
        this.topic = topic;
        this.instructions = instructions;
        this.quizRecordStatus = quizRecordStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getDateExpire() {
        return dateExpire;
    }

    public void setDateExpire(Date dateExpire) {
        this.dateExpire = dateExpire;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public boolean getDraft() {
        return isDraft;
    }

    public void setDraft(boolean draft) {
        isDraft = draft;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getHourExpire() {
        return hourExpire;
    }

    public void setHourExpire(int hourExpire) {
        this.hourExpire = hourExpire;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getQuizRecordStatus() {
        return quizRecordStatus;
    }

    public void setQuizRecordStatus(int quizRecordStatus) {
        this.quizRecordStatus = quizRecordStatus;
    }

    @Override
    public String toString() {
        return "ClassroomQuiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", dateExpire=" + dateExpire +
                ", hourExpire=" + hourExpire +
                ", createdDate=" + createdDate +
                ", status=" + status +
                ", isDraft=" + isDraft +
                ", user=" + user +
                ", classId=" + classId +
                ", topic=" + topic +
                ", instructions=" + instructions +
                ", quizRecordStatus=" + quizRecordStatus +
                '}';
    }
}