package com.homihq.manager.core.domain;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "t_role")
public class Role implements Serializable {
	
	public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";
	public static final String ROLE_TENANT_ADMIN = "ROLE_TENANT_ADMIN";
	public static final String ROLE_PROJECT_ADMIN = "ROLE_PROJECT_ADMIN";
	public static final String ROLE_PROJECT_USER = "ROLE_PROJECT_USER";
	
	@Id
	private Integer id;
	
	private String name;


    
}
