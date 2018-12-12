package com.kustov.webproject.entity;

import java.util.ArrayList;
import java.util.Objects;

public class Student extends Entity{
    private String name;
    private String surname;
    private String fathername;
    private int group;
    private int course;
    private User user;
    private String info;
    private ArrayList<Mark> marks;

    public Student() {
        user = new User();
        marks = new ArrayList<>();
    }

    public Student(int id, String name, String surname, String fathername, int group, int course, User user, String info,
                   ArrayList<Mark> marks) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.fathername = fathername;
        this.group = group;
        this.course = course;
        this.user = user;
        this.info = info;
        this.marks = marks;
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

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
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

    public ArrayList<Mark> getMarks() {
        return marks;
    }

    public void setMarks(ArrayList<Mark> marks) {
        this.marks = marks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return group == student.group &&
                course == student.course &&
                Objects.equals(name, student.name) &&
                Objects.equals(surname, student.surname) &&
                Objects.equals(fathername, student.fathername) &&
                Objects.equals(user, student.user) &&
                Objects.equals(info, student.info) &&
                Objects.equals(marks, student.marks);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, surname, fathername, group, course, user, info, marks);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", fathername='" + fathername + '\'' +
                ", group=" + group +
                ", course=" + course +
                ", user=" + user +
                ", info='" + info + '\'' +
                '}';
    }
}
