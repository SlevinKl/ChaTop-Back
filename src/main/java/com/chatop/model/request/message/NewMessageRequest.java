package com.chatop.model.request.message;

import lombok.Data;

@Data
public class NewMessageRequest {
	private String message;
	private Integer user_id;
	private Integer rental_id;
	
    public Integer getUserId() {
        return user_id;
    }
    
    public Integer getRentalId() {
        return rental_id;
    }
}
