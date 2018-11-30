package com.example.manualproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ManualprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManualprojectApplication.class, args);
		//new SpringApplicationBuilder(ManualprojectApplication.class).headless(false).run(args);
	}
}
