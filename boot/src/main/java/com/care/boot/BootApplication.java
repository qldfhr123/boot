package com.care.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // 자동으로 제거 
public class BootApplication extends SpringBootServletInitializer{
	// http://loacalhost/logout
	
	@Override
	protected SpringApplicationBuilder createSpringApplicationBuilder() {
		return super.createSpringApplicationBuilder();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
		
	}
	//run as >> maven build 단축키 있는거 packagge
}
	