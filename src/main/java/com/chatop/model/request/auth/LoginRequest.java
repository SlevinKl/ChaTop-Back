package com.chatop.model.request.auth;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginRequest {
	@Email
	private String login;
	private String password;
}
