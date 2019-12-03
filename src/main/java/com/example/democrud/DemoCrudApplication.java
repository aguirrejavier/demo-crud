package com.example.democrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages= {"com.example.democrud"})
public class DemoCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoCrudApplication.class, args);
	}

}
