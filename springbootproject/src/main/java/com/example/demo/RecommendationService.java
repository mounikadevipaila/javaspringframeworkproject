package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {

    private static final Map<String, List<String>> DOMAIN_SKILLS = Map.ofEntries(
            Map.entry("Web Development", List.of("HTML", "CSS", "JavaScript", "React", "Node.js", "TypeScript", "Angular", "Next.js", "Tailwind CSS")),
            Map.entry("Data Science", List.of("Python", "Pandas", "NumPy", "Machine Learning", "Deep Learning", "SQL", "Data Visualization", "TensorFlow", "Scikit-learn")),
            Map.entry("DevOps", List.of("Docker", "Kubernetes", "Jenkins", "CI/CD", "AWS", "Terraform", "Azure DevOps", "Ansible")),
            Map.entry("Mobile Development", List.of("Java", "Kotlin", "Swift", "Flutter", "React Native", "Android SDK", "iOS Development")),
            Map.entry("Cybersecurity", List.of("Network Security", "Penetration Testing", "Ethical Hacking", "Firewalls", "Incident Response", "SIEM", "Cryptography")),
            Map.entry("Cloud Computing", List.of("AWS", "Azure", "Google Cloud Platform", "Serverless", "Cloud Security", "Kubernetes")),
            Map.entry("AI/ML", List.of("Python", "PyTorch", "TensorFlow", "Machine Learning", "Deep Learning", "NLP", "Computer Vision"))
    );

    public Map<String, List<String>> getRecommendationsBasedOnSkills(List<String> employeeSkills) {
        List<String> lowerSkills = employeeSkills.stream()
                .map(s -> s.trim().toLowerCase())
                .toList();

        String detectedDomain = detectDomain(lowerSkills);

        if (detectedDomain.equals("Unknown")) {
            return Map.of("Unknown Domain", List.of("No recommendations available for unknown domain"));
        }

        List<String> missingSkills = DOMAIN_SKILLS.get(detectedDomain).stream()
                .filter(skill -> !lowerSkills.contains(skill.toLowerCase()))
                .toList();

        return Map.of(detectedDomain, missingSkills);
    }

    private String detectDomain(List<String> skillsLower) {
        if (skillsLower.stream().anyMatch(s -> s.contains("html") || s.contains("react") || s.contains("javascript")))
            return "Web Development";
        if (skillsLower.stream().anyMatch(s -> s.contains("python") || s.contains("pandas") || s.contains("numpy")))
            return "Data Science";
        if (skillsLower.stream().anyMatch(s -> s.contains("docker") || s.contains("kubernetes") || s.contains("ci/cd")))
            return "DevOps";
        if (skillsLower.stream().anyMatch(s -> s.contains("kotlin") || s.contains("swift") || s.contains("flutter")))
            return "Mobile Development";
        if (skillsLower.stream().anyMatch(s -> s.contains("security") || s.contains("hacking") || s.contains("firewall")))
            return "Cybersecurity";
        if (skillsLower.stream().anyMatch(s -> s.contains("aws") || s.contains("azure") || s.contains("cloud")))
            return "Cloud Computing";
        if (skillsLower.stream().anyMatch(s -> s.contains("tensorflow") || s.contains("pytorch") || s.contains("nlp")))
            return "AI/ML";

        return "Unknown";
    }
}
