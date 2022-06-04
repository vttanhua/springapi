package com.example.sa.security;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.example.sa.entity.ExchangeRate;
import com.example.sa.entity.Role;
import com.example.sa.enums.Currency;

import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class JwtProviderTest {
	

	private String secretKey="abcdxyzvv";

    private long validityInMilliseconds=2000l;
    
	private JwtProvider jwtProvider;
	
	private String token;
	
	@BeforeEach
	 void setUpBeforeEach() throws Exception {
		jwtProvider = new JwtProvider(secretKey,validityInMilliseconds);
		log.info("testCreateToken()*****");
		String username="tanhuves";
		List<Role> roles = new ArrayList<>();
		Role testRole = new Role();
		testRole.setRoleName("test_role");
		roles.add(testRole);
		token = jwtProvider.createToken(username, roles);
	}
	
	@Test
	void testTokenExpiration() throws InterruptedException {
		log.info("testTokenExpiration(): {}",token);
		Thread.sleep(validityInMilliseconds+1000);
		
		boolean isValid = jwtProvider.isValidToken(token);
		log.info("is valid token? {}", isValid);
		assertTrue(!isValid);
	}
	
	@Test
	void testTokenNotExpiration() throws InterruptedException {
		log.info("testTokenExpiration(): {}",token);
		
		boolean isValid = jwtProvider.isValidToken(token);
		log.info("is valid token? {}", isValid);
		log.info("Username is {}", jwtProvider.getUsername(token));	
		assertTrue(isValid);
	}
	
	@Test
	void testCreateToken() {
		
		log.info("Generated token is: {}",token);
		
		log.info("Username is {}", jwtProvider.getUsername(token));		
	}


	
}
