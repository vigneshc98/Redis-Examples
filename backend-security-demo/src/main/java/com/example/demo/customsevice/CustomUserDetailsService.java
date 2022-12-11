package com.example.demo.customsevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.exception.UserNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		GrantedAuthority auth = new SimpleGrantedAuthority("ADMIN");
		
		List<GrantedAuthority>  list = new ArrayList<>();
		list.add(auth);
		
		if(username.equals("vignesh")) {
			
			return new User("vignesh","{noop}test1234", list);
			
		}
		else {
			throw new UserNotFoundException("User not found");
		}
	}

}
