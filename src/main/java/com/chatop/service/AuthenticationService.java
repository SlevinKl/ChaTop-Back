package com.chatop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.entity.UserEntity;
import com.chatop.exception.NotFoundException;
import com.chatop.exception.ResourceAlreadyExistsException;
import com.chatop.factory.UserFactory;
import com.chatop.model.request.auth.LoginRequest;
import com.chatop.model.request.auth.RegisterRequest;
import com.chatop.repository.UserRepository;

@Service
public class AuthenticationService {

	private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
	
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
	public Authentication loginUser(LoginRequest loginRequest) {
		String email = loginRequest.getLogin();
		String password = loginRequest.getPassword();

        UserEntity user = Optional.ofNullable(userRepository.findByEmail(email))
    		.orElseThrow(() -> new NotFoundException("User not exist with email: " + email));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Bad password");
        }

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        UserDetails userDetails = new User(user.getEmail(), user.getPassword(), authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

	public UserEntity registerUser(RegisterRequest registerRequest) {
		if (userRepository.existsByEmail(registerRequest.getEmail())) {
	    	throw new ResourceAlreadyExistsException("Email already used, please choose another one.");
		} else {
			UserEntity user = UserFactory.createUserFromRequest(registerRequest, passwordEncoder);
			return this.userRepository.save(user);
		}
	}
	
	public UserEntity getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {    
			String email = authentication.getName();
			return userRepository.findByEmail(email);
		} else {
			throw new AccessDeniedException("User is not authenticated.");
		}
    }
}
