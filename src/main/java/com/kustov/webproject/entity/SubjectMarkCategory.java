package com.kustov.webproject.entity;

import java.time.LocalDate;
import java.util.Objects;

public class SubjectMarkCategory extends Entity {
    private String category;
    private LocalDate date;

    public SubjectMarkCategory() {
        date = null;
    }

    public SubjectMarkCategory(int id, String category, LocalDate date){
        super(id);
        this.category = category;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        SubjectMarkCategory that = (SubjectMarkCategory) o;
        return Objects.equals(category, that.category) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), category, date);
    }

    @Override
    public String toString() {
        return "SubjectMarkCategory{" +
                "category='" + category + '\'' +
                ", date=" + date +
                '}';
    }
}
