package com.nsa.nsa_spring_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NsaSpringAppApplication {

	public static void main(String[] args) {
        SpringApplication.run(NsaSpringAppApplication.class, args);
	}

}
