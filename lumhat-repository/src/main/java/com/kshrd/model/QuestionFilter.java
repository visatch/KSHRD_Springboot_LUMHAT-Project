package com.kshrd.model;

public class QuestionFilter {
    private int quiz_id;
    private int user_id;
    private boolean isRandom;


    public QuestionFilter() {
    }

    public QuestionFilter(int quiz_id, int user_id, boolean isRandom) {
        this.quiz_id = quiz_id;
        this.user_id = user_id;
        this.isRandom = isRandom;
    }

    public boolean getIsRandom() {
        return isRandom;
    }

    public void setIsRandom(boolean random) {
        isRandom = random;
    }

    public int getQuiz_id() {

        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
