package com.kshrd.model.form;

public class SubMajorForm {
    private int id;
    private String sub_major_name;
    private int major_id;

    public SubMajorForm(int id, String sub_major_name, int major_id) {
        this.id = id;
        this.sub_major_name = sub_major_name;
        this.major_id = major_id;
    }

    public SubMajorForm(){}

    @Override
    public String toString() {
        return "SubMajorForm{" +
                "id=" + id +
                ", sub_major_name='" + sub_major_name + '\'' +
                ", major_id=" + major_id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSub_major_name() {
        return sub_major_name;
    }

    public void setSub_major_name(String sub_major_name) {
        this.sub_major_name = sub_major_name;
    }

    public int getMajor_id() {
        return major_id;
    }

    public void setMajor_id(int major_id) {
        this.major_id = major_id;
    }
}
