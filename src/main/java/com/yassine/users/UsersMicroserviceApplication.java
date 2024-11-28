package com.yassine.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.yassine.users.entities.Role;
import com.yassine.users.entities.User;
import com.yassine.users.service.UserService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class UsersMicroserviceApplication {
	
	@Autowired
	UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(UsersMicroserviceApplication.class, args);
	}
	/*
	@PostConstruct
	void init_users() {
		 userService.addRole(new Role(null,"ADMIN"));
		 userService.addRole(new Role(null,"USER"));
	
		 userService.saveUser(new User(null,"admin","123",true,null));
		 
		 userService.saveUser(new User(null,"nadhem","123",true,null));
		 userService.saveUser(new User(null,"yassine","123",true,null));
		 
		 userService.addRoleToUser("admin", "ADMIN");
		 userService.addRoleToUser("admin", "USER");
		 userService.addRoleToUser("nadhem", "USER");
		 userService.addRoleToUser("yassine", "USER");
	 }*/
	 
	
}
