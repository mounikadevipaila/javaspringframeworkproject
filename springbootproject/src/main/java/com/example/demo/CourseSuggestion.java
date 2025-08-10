package com.example.demo;

public class CourseSuggestion {
    private String skill;
    private String courseName;
    private String courseUrl;

    public CourseSuggestion(String skill, String courseName, String courseUrl) {
        this.skill = skill;
        this.courseName = courseName;
        this.courseUrl = courseUrl;
    }

    public String getSkill() { return skill; }
    public String getCourseName() { return courseName; }
    public String getCourseUrl() { return courseUrl; }
}
