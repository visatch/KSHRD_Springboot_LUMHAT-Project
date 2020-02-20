package com.kshrd.model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
    private int id;
    private String roles;
    private User user;
    private static final long serialVersionUID = 1L;

    public Role(int id, String role) {
        this.id = id;
        this.roles = role;
    }

    public Role(int id, String roles, User user) {
        this.id = id;
        this.roles = roles;
        this.user = user;
    }

    public Role() {

    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + roles + '\'' +
                '}';
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return roles;
    }

    public void setRole(String role) {
        this.roles = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getAuthority() {
        return  "ROLE_"+roles;
    }
}
