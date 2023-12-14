package com.chatop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chatop.entity.RentalEntity;
import com.chatop.entity.UserEntity;
import com.chatop.exception.InternalServerErrorException;
import com.chatop.exception.NotFoundException;
import com.chatop.exception.ForbiddenException;
import com.chatop.factory.RentalFactory;
import com.chatop.model.request.rental.EditRentalRequest;
import com.chatop.model.request.rental.NewRentalRequest;
import com.chatop.model.response.rental.RentalResponse;
import com.chatop.repository.RentalRepository;

@Service
public class RentalService {

	private RentalRepository rentalRepository;
	private AuthenticationService authService;

   public RentalService(RentalRepository rentalRepository, AuthenticationService authService) {
        this.rentalRepository = rentalRepository;
        this.authService = authService;
    }
	
	public List<RentalResponse> getRentals() {
		List<RentalEntity> rentals = rentalRepository.findAll();
		List<RentalResponse> rentalResponses = new ArrayList<>();
		rentals.stream().forEach(rentalEntity -> {
			rentalResponses.add(new RentalResponse(rentalEntity));
		});
		return rentalResponses;
	}

	public RentalEntity findRental(Integer id) {
		return rentalRepository
				.findById(id)
				.orElseThrow(() ->new NotFoundException("Rental not found, ID: " + id));
	}

	public RentalEntity createRental(NewRentalRequest newRentalRequest) {
		UserEntity owner = authService.getCurrentUser();
		RentalEntity rental = RentalFactory.createRentalFromRequest(newRentalRequest, owner);
		try {
			return rentalRepository.save(rental);
		} catch (Exception e) {
			throw new InternalServerErrorException("Error saving rental: " + e.getMessage());
		}
	}

	public RentalEntity updateRental(Integer id, EditRentalRequest editRentalRequest) {				
	    RentalEntity rental = rentalRepository.findById(id)
	            .orElseThrow(() -> new NotFoundException("Rental not found, ID: " + id));

	    if (!authService.getCurrentUser().getId().equals(rental.getOwner().getId())) {
	        throw new ForbiddenException("You do not have permission to update this rental.");
	    }

	    rental.setName(editRentalRequest.getName());
	    rental.setSurface(editRentalRequest.getSurface());
	    rental.setPrice(editRentalRequest.getPrice());
	    rental.setDescription(editRentalRequest.getDescription());

	    try {
	        return rentalRepository.save(rental);
	    } catch (Exception e) {
	        throw new InternalServerErrorException("Error updating rental, ID " + id + ": " + e.getMessage());
	    }
	}
}
