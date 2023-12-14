package com.chatop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.entity.RentalEntity;
import com.chatop.model.request.rental.EditRentalRequest;
import com.chatop.model.request.rental.NewRentalRequest;
import com.chatop.model.response.rental.RentalResponse;
import com.chatop.model.response.rental.RentalsResponse;
import com.chatop.service.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")
public class RentalController {
	private RentalService rentalService;

	public RentalController(RentalService rentalService) {
		this.rentalService = rentalService;
	}

	@Operation(
            summary = "Get all Rental",
            description = "Get a list of all rental ",
            security = { @SecurityRequirement(name = "bearer-key") })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401")
    })
	@GetMapping("/rentals")
	public ResponseEntity<RentalsResponse> rentals() {
		List<RentalResponse> listRentalsResponse =  rentalService.getRentals();
		RentalsResponse response = new RentalsResponse(listRentalsResponse);
		return ResponseEntity.ok(response);
	}

	@Operation(
            summary = "Update rental",
            description = "Update a rental information (name, surface, price, description)." +
                    "If there is no name then a name will be given by default ",
            security = { @SecurityRequirement(name = "bearer-key") })
	@SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401")
    })
	@GetMapping("/rentals/{id}")
	public ResponseEntity<RentalResponse> rental(@PathVariable Integer id) {
		RentalEntity rental = rentalService.findRental(id);
		RentalResponse response = new RentalResponse(rental);
		return ResponseEntity.ok(response);
	}

	@Operation(
            summary = "Create rental",
            description = "Create a rental with some information (surface, price, description) and a picture." +
                    "If there is no name then a name will be given by default ",
            security = { @SecurityRequirement(name = "bearer-key") })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401")
    })
	@PostMapping("/rentals")
	public ResponseEntity<String> newRental(@ModelAttribute NewRentalRequest newRentalRequest) {
		rentalService.createRental(newRentalRequest);
		return ResponseEntity.ok("Rental created !");
	}

	@Operation(
            summary = "Update rental",
            description = "Update a rental information (name, surface, price, description)." +
                    "If there is no name then a name will be given by default ",
            security = { @SecurityRequirement(name = "bearer-key") })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401")
    })
	@PutMapping("/rentals/{id}")
	public ResponseEntity<String> editRental(@PathVariable Integer id, @ModelAttribute EditRentalRequest editRentalRequest) {
		rentalService.updateRental(id, editRentalRequest);
		return ResponseEntity.ok("Rental updated !");
	}
}
