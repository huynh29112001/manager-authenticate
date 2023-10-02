package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.demo")
public class DemoApplication {

	public static void main(String[] args) {
		System.out.println("hello");
		System.out.println("hello");
		
		SpringApplication.run(DemoApplication.class, args);
	}

}
