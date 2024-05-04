package com.example.sajid_waiting_list_management;

public class Student {
    private int id; // Assuming ID is an integer
    private String name;
    private String course;
    private String priority;

    // Constructor
    public Student(int id, String name, String course, String priority) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.priority = priority;
    }

    // Getter and setter methods

    // ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Course
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    // Priority
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
