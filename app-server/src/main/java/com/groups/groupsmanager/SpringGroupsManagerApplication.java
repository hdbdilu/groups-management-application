package com.groups.groupsmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.groups.groupsmanager.config.ApplicationConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration.class)
public class SpringGroupsManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGroupsManagerApplication.class, args);
	}

}
