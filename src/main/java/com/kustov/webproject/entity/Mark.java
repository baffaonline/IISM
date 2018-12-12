package com.kustov.webproject.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Mark extends Entity{
    private Student student;
    private Teacher teacher;
    private String mark;
    private Subject subject;
    private SubjectMarkCategory type;
    private LocalDate date;

    public Mark() {
        student = new Student();
        teacher = new Teacher();
        subject = new Subject();
        type = new SubjectMarkCategory();
        date = LocalDate.now();
    }

    public Mark(int id, Student student, Teacher teacher, String mark, Subject subject, SubjectMarkCategory type,
                LocalDate date) {
        super(id);
        this.student = student;
        this.teacher = teacher;
        this.mark = mark;
        this.subject = subject;
        this.type = type;
        this.date = date;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public SubjectMarkCategory getType() {
        return type;
    }

    public void setType(SubjectMarkCategory type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Mark mark1 = (Mark) o;
        return Objects.equals(student, mark1.student) &&
                Objects.equals(teacher, mark1.teacher) &&
                Objects.equals(mark, mark1.mark) &&
                Objects.equals(subject, mark1.subject) &&
                type == mark1.type &&
                Objects.equals(date, mark1.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), student, teacher, mark, subject, type, date);
    }

    @Override
    public String toString() {
        return "Mark{" +
                "student=" + student +
                ", teacher=" + teacher +
                ", mark='" + mark + '\'' +
                ", subject=" + subject +
                ", type=" + type +
                ", date=" + date +
                '}';
    }
}
