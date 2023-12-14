package com.chatop.model.response.rental;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import com.chatop.entity.RentalEntity;

import lombok.Data;

@Data
public class RentalResponse {

    private Integer id;
    private String name;
    private Integer surface;
    private Integer price;
    private List<String> picture;
    private String description;
    private Integer owner_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public RentalResponse(RentalEntity rentalEntity) {
        this.id = rentalEntity.getId();
        this.name = rentalEntity.getName();
        this.surface = rentalEntity.getSurface();
        this.price = rentalEntity.getPrice();
        this.picture = Collections.singletonList(rentalEntity.getPicture());
        this.description = rentalEntity.getDescription();
        this.owner_id = rentalEntity.getOwner().getId();
        this.created_at = rentalEntity.getCreatedAt();
        this.updated_at = rentalEntity.getUpdatedAt();
    }
}