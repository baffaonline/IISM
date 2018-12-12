package com.kustov.webproject.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Subject extends Entity{
    private String name;
    private Teacher teacher;
    private int group;
    private int course;
    private ArrayList<LocalDate> dates;
    private ArrayList<SubjectMarkCategory> categories;

    public Subject() {
        teacher = new Teacher();
        dates = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public Subject(int id, String name, Teacher teacher, int group, int course, ArrayList<LocalDate> dates,
                   ArrayList<SubjectMarkCategory> categories) {
        super(id);
        this.name = name;
        this.teacher = teacher;
        this.group = group;
        this.course = course;
        this.dates = dates;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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

    public ArrayList<LocalDate> getDates() {
        return dates;
    }

    public void setDates(ArrayList<LocalDate> dates) {
        this.dates = dates;
    }

    public ArrayList<SubjectMarkCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<SubjectMarkCategory> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subject subject = (Subject) o;
        return group == subject.group &&
                course == subject.course &&
                Objects.equals(name, subject.name) &&
                Objects.equals(teacher, subject.teacher) &&
                Objects.equals(dates, subject.dates) &&
                Objects.equals(categories, subject.categories);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, teacher, group, course, dates, categories);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", teacher=" + teacher +
                ", group=" + group +
                ", course=" + course +
                '}';
    }
}
