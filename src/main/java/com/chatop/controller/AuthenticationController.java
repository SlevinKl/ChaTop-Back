package com.chatop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.model.request.auth.LoginRequest;
import com.chatop.model.request.auth.RegisterRequest;
import com.chatop.model.response.auth.LoginResponse;
import com.chatop.service.AuthenticationService;
import com.chatop.service.JWTService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	public JWTService jwtService;
	private AuthenticationService authService;
	
	public AuthenticationController(AuthenticationService authService, JWTService jwtService) {
		this.authService = authService;
		this.jwtService = jwtService;
	}

	@Operation(
            summary = "Login to chatop and get a token",
            description = "Get a token access by sending a Login object to login ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400")
    })
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authService.loginUser(loginRequest);
		String token = jwtService.generateToken(authentication);
		LoginResponse response = new LoginResponse(token);
		return response;
	}
	
	@Operation(
            summary = "Sign up to chatop",
            description = "Send a Register object (name, email, password) to sign up")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
    	authService.registerUser(registerRequest);
        return ResponseEntity.ok("");
	}

	@Operation(
            summary = "Get user info",
            description = "get information about user account",
            security = { @SecurityRequirement(name = "bearer-key") })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401")
    })
    @GetMapping("/me")
    public ResponseEntity<String> me() {
      authService.getCurrentUser();
      return ResponseEntity.ok("");
    }
}
