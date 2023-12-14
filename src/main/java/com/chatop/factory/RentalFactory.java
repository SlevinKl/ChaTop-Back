package com.chatop.factory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.Path;
import com.chatop.entity.RentalEntity;
import com.chatop.entity.UserEntity;
import com.chatop.model.request.rental.NewRentalRequest;

public class RentalFactory {

	public static RentalEntity createRentalFromRequest(NewRentalRequest newRentalRequest, UserEntity owner) {
        RentalEntity rental = new RentalEntity();
        rental.setName(newRentalRequest.getName());
        rental.setSurface(newRentalRequest.getSurface());
        rental.setPrice(newRentalRequest.getPrice());
        rental.setDescription(newRentalRequest.getDescription());
        rental.setOwner(owner);

        if (newRentalRequest.getPicture() != null) {
        	Path destinationFile = Path.of("/Users/rayanemoula/Desktop//ChaTop-Pictures/" + newRentalRequest.getPicture().getOriginalFilename());
	        try {
	        	Files.copy(newRentalRequest.getPicture().getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
	            rental.setPicture(destinationFile.toString());
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
        }

        return rental;
	}
}
