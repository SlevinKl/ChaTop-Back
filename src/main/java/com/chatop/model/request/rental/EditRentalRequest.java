package com.chatop.model.request.rental;

import lombok.Data;

@Data
public class EditRentalRequest {
	  private String name;
	  private Integer surface;
	  private Integer price;
	  private String description;
}
