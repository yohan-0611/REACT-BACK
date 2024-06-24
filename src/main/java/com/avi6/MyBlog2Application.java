package com.avi6;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class MyBlog2Application {

	public static void main(String[] args) {
		SpringApplication.run(MyBlog2Application.class, args);
	}

}
