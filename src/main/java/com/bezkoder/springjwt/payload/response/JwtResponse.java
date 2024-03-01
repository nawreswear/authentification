package com.bezkoder.springjwt.payload.response;

import lombok.Getter;

public class JwtResponse {
	private String token;
	@Getter
	private String type;
	private Long id;
	private String password;
	private String email;

	public JwtResponse(String accessToken, Long id, String password, String email, String type) {
		this.token = accessToken;
		this.id = id;
		this.password = password;
		this.email = email;
		this.type = type;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}
