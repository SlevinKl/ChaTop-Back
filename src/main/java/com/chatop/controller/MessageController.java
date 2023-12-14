package com.chatop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.model.request.message.NewMessageRequest;
import com.chatop.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class MessageController {
	private MessageService messageService;

	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@Operation(
            summary = "Post a message",
            description = "A user can post a message about a rental",
            security = { @SecurityRequirement(name = "bearer-key") })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401")
    })
	@PostMapping("/api/messages")
    public ResponseEntity<String> sendMessage(@RequestBody NewMessageRequest newMessageRequest) {
		messageService.sendMessage(newMessageRequest);
		return ResponseEntity.ok("Message send with success");
	}
}
