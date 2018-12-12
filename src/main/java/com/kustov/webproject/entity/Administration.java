package com.kustov.webproject.entity;

import java.util.Objects;

public class Administration extends Entity{
    private String name;
    private String surname;
    private String fathername;
    private User user;
    private String info;

    public Administration() {
        user = new User();
    }

    public Administration(int id, String name, String surname, String fathername, User user, String info) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.fathername = fathername;
        this.user = user;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Administration teacher = (Administration) o;
        return Objects.equals(name, teacher.name) &&
                Objects.equals(surname, teacher.surname) &&
                Objects.equals(fathername, teacher.fathername) &&
                Objects.equals(user, teacher.user) &&
                Objects.equals(info, teacher.info);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, surname, fathername, user, info);
    }

    @Override
    public String toString() {
        return "Administration{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", fathername='" + fathername + '\'' +
                ", user=" + user +
                ", info='" + info + '\'' +
                '}';
    }
}
