package com.chatop.model.request.rental;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class NewRentalRequest {
	  private String name;
	  private Integer surface;
	  private Integer price;
	  private MultipartFile picture;
	  private String description;
}
