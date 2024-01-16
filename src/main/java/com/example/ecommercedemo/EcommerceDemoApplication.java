package com.example.ecommercedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
//@ComponentScan(basePackages = "com.example.ecommerce.service")
public class EcommerceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceDemoApplication.class, args);
	}

}
