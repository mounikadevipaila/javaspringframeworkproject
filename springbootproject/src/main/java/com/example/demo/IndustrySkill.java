package com.example.demo;

import jakarta.persistence.*;

@Entity
public class IndustrySkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skillName;
    private String category; // optional e.g., "Cloud", "Programming"

    public IndustrySkill() {}
    public IndustrySkill(String skillName, String category) { this.skillName = skillName; this.category = category; }

    // getters/setters
    public Long getId() { return id; }
    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
