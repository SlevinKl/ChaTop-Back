package com.chatop.service;

import org.springframework.stereotype.Service;
import com.chatop.entity.UserEntity;
import com.chatop.exception.NotFoundException;
import com.chatop.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	public UserEntity findUser(Integer id) {
		return this.userRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("User not found, ID: " + id));
	}
}
