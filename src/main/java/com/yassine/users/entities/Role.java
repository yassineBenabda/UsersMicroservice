package com.yassine.users.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor 
@Entity 
public class Role { 
@Id  
@GeneratedValue (strategy=GenerationType.IDENTITY)  
private Long role_id; 
private String role; 
} 