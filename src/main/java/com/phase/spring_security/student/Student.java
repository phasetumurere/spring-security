package com.phase.spring_security.student;

public class Student {
    private final Integer studentId;
    private final String studentNames;

    public Student(Integer studentId, String studentNames) {
        this.studentId = studentId;
        this.studentNames = studentNames;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getStudentNames() {
        return studentNames;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentNames='" + studentNames + '\'' +
                '}';
    }
}
