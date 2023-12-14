package com.chatop.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
public class SpringSecurityConfig {

	@Autowired
	private JWTProperties jwtProperties;
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http
    		.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authorizeRequest -> 
				authorizeRequest
					.requestMatchers("/api/auth/register").permitAll()
					.requestMatchers("/api/auth/login").permitAll()
                    .requestMatchers("/doc/**").permitAll()
					.anyRequest().authenticated()
			)
			.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
			
		return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
    	SecretKeySpec secretKey = new SecretKeySpec(this.jwtProperties.getJwtKey().getBytes(), 0, this.jwtProperties.getJwtKey().getBytes().length,"RSA");
    	return NimbusJwtDecoder
    			.withSecretKey(secretKey)
				.macAlgorithm(MacAlgorithm.HS256)
				.build();
    }

    @Bean
	public JwtEncoder jwtEncoder() {
    	return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtProperties.getJwtKey().getBytes()));
	}

    @Bean
    public UserDetailsService users() {
    	UserDetails user = User
    						.builder()
							.username("login")
							.password(passwordEncoder().encode("password"))
							.roles("USER")
							.build();
    	return new InMemoryUserDetailsManager(user);
    }
}
