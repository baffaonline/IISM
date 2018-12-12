package com.kustov.webproject.service;

import java.util.Objects;

public class GroupCoursePair {
    private int group;
    private int course;

    public GroupCoursePair(int group, int course) {
        this.group = group;
        this.course = course;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupCoursePair that = (GroupCoursePair) o;
        return group == that.group &&
                course == that.course;
    }

    @Override
    public int hashCode() {

        return Objects.hash(group, course);
    }

    @Override
    public String toString() {
        return "GroupCoursePair{" +
                "group=" + group +
                ", course=" + course +
                '}';
    }
}
