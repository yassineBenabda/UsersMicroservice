package com.yassine.users.service;

import com.yassine.users.entities.Role;
import com.yassine.users.entities.User;

public interface UserService {
	
	User saveUser(User user);
	User findUserByUsername (String username);
	Role addRole(Role role);
	User addRoleToUser(String username, String rolename);

}
