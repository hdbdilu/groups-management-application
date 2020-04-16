package com.groups.groupsmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.groups.groupsmanager.config.ApplicationConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration.class)
public class GroupManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroupManagementApplication.class, args);
	}

}
