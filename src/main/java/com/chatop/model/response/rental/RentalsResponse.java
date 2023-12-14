package com.chatop.model.response.rental;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RentalsResponse {
	
	@JsonProperty("rentals")
    private List<RentalResponse> rentals;
	
    public RentalsResponse(List<RentalResponse> rentalResponses) {
        this.rentals = rentalResponses;
    }
}
