package com.viseo.fes.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.viseo.fes.java")
public class ApplicationViseo {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationViseo.class, args);
	}
}
