package com.chatop.model.request.auth;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class RegisterRequest {
	@Email
	private String email;
	private String name;
	private String password;
}
