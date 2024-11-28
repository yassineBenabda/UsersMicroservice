package com.yassine.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yassine.users.entities.Role;
import com.yassine.users.entities.User;
import com.yassine.users.repos.RoleRepository;
import com.yassine.users.repos.UserRepository;

import jakarta.transaction.Transactional;

@Transactional 
@Service 
public class UserServiceImpl  implements UserService{ 
 
 @Autowired 
 UserRepository userRepository; 
  
 @Autowired 
 RoleRepository roleRepository; 
  
  
 @Autowired 
 BCryptPasswordEncoder bCryptPasswordEncoder; 
  
 @Override 
 public User saveUser(User user) { 
   
  user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); 
  return userRepository.save(user); 
 } 
 
 @Override 
 public User addRoleToUser(String username, String rolename) { 
  User usr = userRepository.findByUsername(username); 
  Role role = roleRepository.findByRole(rolename); 
   
  usr.getRoles().add(role); 
  return usr; 
 } 
 
 @Override 
 public Role addRole(Role role) { 
  return roleRepository.save(role); 
 }
 
 @Override 
 public User findUserByUsername(String username) {  
  return userRepository.findByUsername(username); 
 }
 
 @Override 
 public List<User> findAllUsers() { 
 return userRepository.findAll(); 
 }
 
}