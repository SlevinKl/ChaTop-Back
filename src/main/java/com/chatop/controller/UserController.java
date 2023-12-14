package com.chatop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.entity.UserEntity;
import com.chatop.model.response.user.UserResponse;
import com.chatop.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class UserController {   
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Operation(
            summary = "Get a user",
            description = "Get a user information with an ID",
            security = { @SecurityRequirement(name = "bearer-key") })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401")
    })
    @GetMapping("/api/user/{id}")
    public ResponseEntity<UserResponse> user(@PathVariable Integer id) {
    	 UserEntity user =  userService.findUser(id);
    	 UserResponse response = new UserResponse(user);
         return ResponseEntity.ok(response);
    }
}
