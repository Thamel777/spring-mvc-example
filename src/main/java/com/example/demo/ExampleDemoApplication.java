package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ExampleDemoApplication extends SpringBootServletInitializer {

	// Enables WAR deployment to an external servlet container
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ExampleDemoApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ExampleDemoApplication.class, args);
	}

}
