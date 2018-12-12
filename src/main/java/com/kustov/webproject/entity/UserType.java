package com.kustov.webproject.entity;


/**
 * The Enum UserType.
 */
public enum UserType {

    STUDENT("student"),
    TEACHER("teacher"),
    ADMINISTRATION("administration"),
    GUEST("guest");

    private String typeName;

    UserType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
