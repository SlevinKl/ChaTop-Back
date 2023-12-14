package com.chatop.service;

import org.springframework.stereotype.Service;

import com.chatop.entity.MessageEntity;
import com.chatop.entity.RentalEntity;
import com.chatop.entity.UserEntity;
import com.chatop.exception.InternalServerErrorException;
import com.chatop.factory.MessageFactory;
import com.chatop.model.request.message.NewMessageRequest;
import com.chatop.repository.MessageRepository;

@Service
public class MessageService {

	private MessageRepository messageRepository;
	private UserService userService;
	private RentalService rentalService;
	
	public MessageService(MessageRepository messageRepository, UserService userService, RentalService rentalService) {
		this.messageRepository = messageRepository;
		this.userService = userService;
		this.rentalService = rentalService;
	}
	
	public MessageEntity sendMessage(NewMessageRequest newMessageRequest) {
		RentalEntity rental = rentalService.findRental(newMessageRequest.getRentalId());
		UserEntity user = userService.findUser(newMessageRequest.getUserId());

		MessageEntity message = MessageFactory.createMessageFromRequest(newMessageRequest, user, rental);
		try {
			return messageRepository.save(message);
		} catch (Exception e) {
			throw new InternalServerErrorException("Error saving message: " + e.getMessage());
		}
	}
}
