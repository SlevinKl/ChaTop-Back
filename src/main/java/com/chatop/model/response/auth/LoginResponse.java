package com.chatop.model.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {

	@JsonProperty("token")
    private String jwtToken;
	
    public LoginResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
