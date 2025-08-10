package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private SkillRepository skillRepo;

    // ---------------------- Add Employee ----------------------
    @PostMapping
    public Employee addEmployee(@RequestBody Employee emp) {
        return employeeRepo.save(emp);
    }

    // ---------------------- Get All Employees ----------------------
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    // ---------------------- Delete Employee ----------------------
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeRepo.deleteById(id);
    }

    // ---------------------- Get Skills for an Employee ----------------------
    @GetMapping("/{id}/skills")
    public List<Skill> getSkills(@PathVariable Long id) {
        Employee emp = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return emp.getSkills();
    }

    // ---------------------- Add Skill to an Employee ----------------------
    @PostMapping("/{id}/skills")
    public Skill addSkill(@PathVariable Long id, @RequestBody Skill skill) {
        Employee emp = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        emp.addSkill(skill);
        return skillRepo.save(skill);
    }

    // ---------------------- Skill Recommendations ----------------------
    private static final Map<String, List<String>> DOMAIN_SKILLS = Map.ofEntries(
            Map.entry("Web Development", List.of("HTML", "CSS", "JavaScript", "React", "Node.js", "TypeScript", "Angular", "Next.js", "Tailwind CSS")),
            Map.entry("Data Science", List.of("Python", "Pandas", "NumPy", "Machine Learning", "Deep Learning", "SQL", "Matplotlib", "Seaborn", "Scikit-learn")),
            Map.entry("DevOps", List.of("Docker", "Kubernetes", "Jenkins", "CI/CD", "AWS", "Terraform", "Ansible", "Prometheus", "Grafana")),
            Map.entry("Cloud Computing", List.of("AWS", "Azure", "Google Cloud", "CloudFormation", "Serverless", "Kubernetes", "DevOps Basics")),
            Map.entry("Mobile Development", List.of("Java", "Kotlin", "Swift", "Flutter", "React Native", "Dart", "Xcode")),
            Map.entry("Cybersecurity", List.of("Network Security", "Ethical Hacking", "Penetration Testing", "Firewalls", "Cryptography", "Incident Response")),
            Map.entry("Artificial Intelligence", List.of("Machine Learning", "Deep Learning", "Natural Language Processing", "TensorFlow", "PyTorch", "Computer Vision")),
            Map.entry("Blockchain", List.of("Solidity", "Ethereum", "Smart Contracts", "Web3.js", "Truffle", "Hyperledger")),
            Map.entry("Game Development", List.of("Unity", "C#", "Unreal Engine", "C++", "Game Physics", "3D Modeling", "Blender")),
            Map.entry("Embedded Systems", List.of("C", "C++", "Microcontrollers", "Arduino", "Raspberry Pi", "RTOS", "IoT Protocols")),
            Map.entry("Internet of Things", List.of("IoT Security", "MQTT", "Node-RED", "Embedded C", "Sensors", "Wireless Communication")),
            Map.entry("UI/UX Design", List.of("Figma", "Adobe XD", "Sketch", "Wireframing", "Prototyping", "Design Principles")),
            Map.entry("Data Engineering", List.of("SQL", "Apache Spark", "Hadoop", "ETL", "Kafka", "Data Warehousing")),
            Map.entry("Big Data", List.of("Hadoop", "Spark", "Hive", "Pig", "MapReduce", "NoSQL")),
            Map.entry("Augmented Reality / Virtual Reality", List.of("Unity", "Unreal Engine", "ARKit", "ARCore", "3D Modeling", "VR Design"))
    );

    @GetMapping("/{id}/recommendations")
    public List<String> getRecommendations(@PathVariable Long id) {
        Employee emp = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<String> employeeSkills = emp.getSkills().stream()
                .map(s -> s.getSkillName().trim().toLowerCase())
                .toList();

        String domain = detectDomain(employeeSkills);

        if (!DOMAIN_SKILLS.containsKey(domain)) {
            return List.of("No recommendations available for unknown domain");
        }

        return DOMAIN_SKILLS.get(domain).stream()
                .filter(skill -> !employeeSkills.contains(skill.toLowerCase()))
                .toList();
    }

    private String detectDomain(List<String> skillsLower) {
        if (skillsLower.stream().anyMatch(s -> s.contains("html") || s.contains("react") || s.contains("javascript") || s.contains("css") || s.contains("angular")))
            return "Web Development";
        if (skillsLower.stream().anyMatch(s -> s.contains("python") || s.contains("pandas") || s.contains("numpy") || s.contains("machine learning") || s.contains("deep learning")))
            return "Data Science";
        if (skillsLower.stream().anyMatch(s -> s.contains("docker") || s.contains("kubernetes") || s.contains("ci/cd") || s.contains("jenkins")))
            return "DevOps";
        if (skillsLower.stream().anyMatch(s -> s.contains("aws") || s.contains("azure") || s.contains("google cloud")))
            return "Cloud Computing";
        if (skillsLower.stream().anyMatch(s -> s.contains("java") || s.contains("kotlin") || s.contains("swift") || s.contains("flutter")))
            return "Mobile Development";
        if (skillsLower.stream().anyMatch(s -> s.contains("security") || s.contains("hacking") || s.contains("firewall") || s.contains("cryptography")))
            return "Cybersecurity";
        if (skillsLower.stream().anyMatch(s -> s.contains("ai") || s.contains("pytorch") || s.contains("tensorflow") || s.contains("nlp")))
            return "Artificial Intelligence";
        if (skillsLower.stream().anyMatch(s -> s.contains("solidity") || s.contains("ethereum") || s.contains("blockchain")))
            return "Blockchain";
        if (skillsLower.stream().anyMatch(s -> s.contains("unity") || s.contains("unreal engine") || s.contains("game")))
            return "Game Development";
        if (skillsLower.stream().anyMatch(s -> s.contains("arduino") || s.contains("raspberry") || s.contains("microcontroller")))
            return "Embedded Systems";
        if (skillsLower.stream().anyMatch(s -> s.contains("iot") || s.contains("mqtt") || s.contains("node-red")))
            return "Internet of Things";
        if (skillsLower.stream().anyMatch(s -> s.contains("figma") || s.contains("adobe xd") || s.contains("wireframe")))
            return "UI/UX Design";
        if (skillsLower.stream().anyMatch(s -> s.contains("spark") || s.contains("hadoop") || s.contains("etl") || s.contains("data warehouse")))
            return "Data Engineering";
        if (skillsLower.stream().anyMatch(s -> s.contains("hive") || s.contains("pig") || s.contains("mapreduce") || s.contains("nosql")))
            return "Big Data";
        if (skillsLower.stream().anyMatch(s -> s.contains("arcore") || s.contains("arkit") || s.contains("vr") || s.contains("augmented reality")))
            return "Augmented Reality / Virtual Reality";

        return "Unknown";
    }
}
