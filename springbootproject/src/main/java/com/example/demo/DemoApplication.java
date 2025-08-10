package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(IndustrySkillRepository industryRepo) {
		return args -> {
			if (industryRepo.count() == 0) {
				industryRepo.save(new IndustrySkill("java", "Programming"));
				industryRepo.save(new IndustrySkill("spring", "Framework"));
				industryRepo.save(new IndustrySkill("sql", "Database"));
				industryRepo.save(new IndustrySkill("aws", "Cloud"));
				industryRepo.save(new IndustrySkill("docker", "DevOps"));
			}
		};
	}
}
