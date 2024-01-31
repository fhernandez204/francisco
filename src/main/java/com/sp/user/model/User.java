package com.sp.user.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;
  
  @Column(name = "password")
  private String password;
  
	  @Column(name = "dateCreate") 
	  private String dateCreate;
	  
	  @Column(name = "dateModified") 
	  private String dateModified;
	  
	  @Column(name = "dateLastLogin") 
	  private String dateLastLogin;
	 
	  @Column(name = "token") 
	  private String token;

  @Column(name = "published", nullable = true)
  private Boolean active;
  
  
  @JsonManagedReference
  @OneToMany(fetch = FetchType.LAZY, mappedBy="user", cascade = CascadeType.ALL)
  private List<Phone> phones;

  public User() {

  }

  public User(String name, String email, String password, String token, boolean active,List<Phone> phones) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.token = token;
    this.active = active;
    this.dateCreate = String.valueOf(new Date());
    this.dateModified  = String.valueOf(new Date());
    this.dateLastLogin  = String.valueOf(new Date());
    //this.setPhones(phones);
  }

  public long getId() {
    return id;
  }
  
  public String getName() {
	return name;
}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

  public String getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}

	public String getDateLastLogin() {
		return dateLastLogin;
	}

	public void setDateLastLogin(String dateLastLogin) {
		this.dateLastLogin = dateLastLogin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

@Override
  public String toString() {
    return "User [id=" + id + ", name=" + name + ", email=" + email+ ", password=" + password + ", published=" + active+ ", phones=" + phones.toString() + "]";
  }

}
