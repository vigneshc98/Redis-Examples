package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.reqres.LoginRequest;
import com.example.demo.reqres.LoginResponse;
import com.example.demo.util.JwtTokenUtil;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class AuthenticationController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
		
		Authentication authentication = null;
		
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		User user = (User) authentication.getPrincipal();
		
		String accessToken = null;
		String refreshToken = null;
		
		try {
			accessToken = jwtTokenUtil.generateAccessToken(user);
			refreshToken = jwtTokenUtil.generateRefreshToken(user);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		LoginResponse response = new LoginResponse();
		response.setAccessToken(accessToken);
		response.setRefreshToken(refreshToken);
		
		return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
	}
}
