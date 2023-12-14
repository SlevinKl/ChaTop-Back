package com.chatop.factory;

import com.chatop.entity.MessageEntity;
import com.chatop.entity.RentalEntity;
import com.chatop.entity.UserEntity;
import com.chatop.model.request.message.NewMessageRequest;

public class MessageFactory {
  public static MessageEntity createMessageFromRequest(NewMessageRequest newMessageRequest, UserEntity user, RentalEntity rental) {
	  MessageEntity message = new MessageEntity();
	  message.setMessage(newMessageRequest.getMessage());
	  message.setUser(user);
	  message.setRental(rental);
	  return message;
  }
}
