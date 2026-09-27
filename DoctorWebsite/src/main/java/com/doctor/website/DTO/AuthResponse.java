package com.doctor.website.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponse {
	
	private String token;
	
	public AuthResponse(String token) {
	    this.token = token;
	}

	//setters,getters
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}
