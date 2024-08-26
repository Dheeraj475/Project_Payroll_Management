package com.example.security;


import java.io.Serializable;

public class JwtResponse implements Serializable{
	
	private static final long serialVersionUID = 8869344122384649326L;
	
	private final String jwttoken;

	public String getJwttoken() {
		return jwttoken;
	}

	public JwtResponse(String jwttoken) {
		super();
		this.jwttoken = jwttoken;
	}

	
}
