package com.kshrd.model;

import com.kshrd.model.Role;
import com.kshrd.model.User;

public class UserRole {
    private int id;
    private Role role;
    private User user;

    public UserRole() {
    }

    public UserRole(int id, Role role, User user) {
        this.id = id;
        this.role = role;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", role=" + role +
                ", user=" + user +
                '}';
    }
}
