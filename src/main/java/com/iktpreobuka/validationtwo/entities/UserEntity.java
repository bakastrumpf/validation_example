package com.iktpreobuka.validationtwo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotBlank(message = "First name must not be blank nor null")
	@Size(min = 2, max = 30, message = "First name must be string between {min} and {max}.")
	@Column(nullable = false)
	private String firstName;
	
	@NotBlank(message = "Last name must not be blank nor null")
	@Size(min = 2, max = 30, message = "Last name must be string between {min} and {max}.")
	@Column(nullable = false)
	private String lastName;
	
	@NotNull(message = "Email must not be blank nor null")
	@Email(message = "Email is not valid.")
	//@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
	//		message="Email is not valid.")
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotBlank(message = "Username must not be blank nor null")
	@Size(min = 5, max = 15, message = "Last name must be string between {min} and {max}.")
	@Column(nullable = false)
	private String username;
	
	@NotBlank(message = "Username must not be blank nor null")
	@Size(min = 4, max = 6, message = "Last name must be string between {min} and {max}.")
	@Column(nullable = false)
	private String password;
	
	@NotBlank(message = "Password confirm must not be blank nor null")
	@Size(min = 4, max = 6, message = "Password confirm length must be string between {min} and {max}.")
	@Column(nullable = false)
	private String passwordConfirm;
	
	@Min(value = 18, message = "User must be over 18")
	@Column(nullable = false)
	private Integer age;
	
	@Version
	private Integer version;

	public UserEntity() {
		super();
	}

	public UserEntity(String firstName, String lastName, String email, String username, String password, Integer age) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.age = age;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}