package com.sp.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "phones")
public class Phone {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name = "id_phone")
	  private long idPhone;
	
	  @Column(name = "number")
	  private String number;

	  @Column(name = "citycode")
	  private String citycode;
	  
	  @Column(name = "contrycode")
	  private String contrycode;
	  
	  @JsonBackReference
	  @ManyToOne(cascade = CascadeType.ALL)
	  @JoinColumn(name="ID")
	  private User user;

	  public Phone() {

	  }
	  
	  public Phone(String number, String citycode, String contrycode) {
		    this.number = number;
		    this.citycode = citycode;
		    this.contrycode = contrycode;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getContrycode() {
		return contrycode;
	}

	public void setContrycode(String contrycode) {
		this.contrycode = contrycode;
	}
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	  public String toString() {
	    return "Phone [idPhone=" + idPhone + ", number=" + number + ", citycode=" + citycode+ ", contrycode=" + contrycode + "]";
	  }
	
}
