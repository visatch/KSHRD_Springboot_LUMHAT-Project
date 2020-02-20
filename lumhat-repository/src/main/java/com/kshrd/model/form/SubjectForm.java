package com.kshrd.model.form;

public class SubjectForm {
    private int id;
    private String sub_major_name; // Actually,this is a subject name but because of self-join
                                   // So we don't need to @Result at repository
    private int parent_id;

    public SubjectForm(int id, String sub_major_name, int parent_id) {
        this.id = id;
        this.sub_major_name = sub_major_name;
        this.parent_id = parent_id;
    }
    public SubjectForm(){}

    @Override
    public String toString() {
        return "SubjectForm{" +
                "id=" + id +
                ", sub_major_name='" + sub_major_name + '\'' +
                ", parent_id=" + parent_id +
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

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
