package com.chatop.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix="com.chatop.configuration")
public class JWTProperties {
	private String jwtKey;

}
