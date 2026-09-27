package com.doctor.website.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doctors")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Doctor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int d_id;
	
	private String d_name;
	
	private String d_specialization;
	
	private String d_experience;
	
	private String d_qualification;
	
	private String d_hospital;
	
	private int d_consultationFee;
	
	private String d_availableFrom;
	
	private String d_availableTo;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	// setters, getters
	
	public int getD_id() {
		return d_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setD_id(int d_id) {
		this.d_id = d_id;
	}

	public String getD_name() {
		return d_name;
	}

	public void setD_name(String d_name) {
		this.d_name = d_name;
	}

	public String getD_specialization() {
		return d_specialization;
	}

	public void setD_specialization(String d_specialization) {
		this.d_specialization = d_specialization;
	}

	public String getD_experience() {
		return d_experience;
	}

	public void setD_experience(String d_experience) {
		this.d_experience = d_experience;
	}

	public String getD_qualification() {
		return d_qualification;
	}

	public void setD_qualification(String d_qualification) {
		this.d_qualification = d_qualification;
	}

	public String getD_hospital() {
		return d_hospital;
	}

	public void setD_hospital(String d_hospital) {
		this.d_hospital = d_hospital;
	}

	public int getD_consultationFee() {
		return d_consultationFee;
	}

	public void setD_consultationFee(int d_consultationFee) {
		this.d_consultationFee = d_consultationFee;
	}

	public String getD_availableFrom() {
		return d_availableFrom;
	}

	public void setD_availableFrom(String d_availableFrom) {
		this.d_availableFrom = d_availableFrom;
	}

	public String getD_availableTo() {
		return d_availableTo;
	}

	public void setD_availableTo(String d_availableTo) {
		this.d_availableTo = d_availableTo;
	}

	


	
	
	
	

}
