package com.kustov.webproject.entity;

import java.util.Objects;


/**
 * The Class User.
 */

public class User extends Entity {

    private String username;

    private String password;

    private UserType type;


    public User() {
        type = UserType.GUEST;
    }

    public User(int id, String username, String password, UserType type) {
        super(id);
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                type == user.type;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), username, password, type);
    }

    @Override
    public String toString() {
        return "User : id : " + this.getId() +
                ", username : " + username +
                ", password : " + password +
                ", type : " + type;
    }
}
