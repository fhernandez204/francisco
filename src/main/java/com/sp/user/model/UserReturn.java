package com.sp.user.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

public class UserReturn {

  private long id;
   
	  private String dateCreate; 
	   
	  private String dateModified;
	 
	  private String dateLastLogin;
	  
	  private String token;
	 
  private Boolean active;
  
  

  public UserReturn() {

  }

  public UserReturn(long id, String dateCreate, String dateModified, String dateLastLogin, String token, boolean active) {
    this.id = id;
    this.dateCreate = dateCreate;
    this.dateModified = dateModified;
    this.dateLastLogin = dateLastLogin;
    this.token = token;
    this.active = active;
  }

  public long getId() {
    return id;
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

	@Override
	public String toString() {
		return "UserReturn [id=" + id + ", dateCreate=" + dateCreate + ", dateModified=" + dateModified
				+ ", dateLastLogin=" + dateLastLogin + ", token=" + token + ", active=" + active + "]";
	}

	

}
