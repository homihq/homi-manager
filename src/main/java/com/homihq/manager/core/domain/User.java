package com.homihq.manager.core.domain;

import lombok.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "t_user")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "email")
	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@JsonIgnore
	private String password;

	@OneToOne
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Role role;

	@Column(name = "deleted")
	private boolean deleted;
	

	@LastModifiedDate
	@Column(name = "last_updated_date")
	private LocalDateTime lastUpdatedDate;

	@CreatedDate
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "last_login_date")
	private LocalDateTime lastLoginDate;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "tenant_id", referencedColumnName = "id")
	private Tenant tenant;

	@Column(name = "verification_token")
	private String verificationToken;
	@Column(name = "verification_expiry_date")
	private LocalDateTime verificationTokenExpiry;
	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "email_verified")
	private boolean emailVerified;
}
