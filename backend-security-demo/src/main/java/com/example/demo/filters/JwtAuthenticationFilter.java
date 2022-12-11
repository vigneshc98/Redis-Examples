package com.example.demo.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.util.JwtTokenUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(request.getServletPath().equals("/api/login")) {
			filterChain.doFilter(request, response);
		}
		else {
			
			String accessToken = jwtTokenUtil.getToken(request);
//			System.out.println("accessToken:"+accessToken);
			if(accessToken != null) {
				

				String auths[] = null;
				String username = jwtTokenUtil.getUsernameFromToken(accessToken);
				
				try {
					auths = jwtTokenUtil.getAuthsFromToken(accessToken);
				}
				catch(Exception exc) {
					exc.printStackTrace();
				}
				
//				for(String str :auths) {
//					System.out.println("role:"+str);
//				}
				
				if(username!=null && auths!=null) {
//					System.out.println("username:"+username);
					User user = (User) userDetailsService.loadUserByUsername(username);
//					System.out.println("user:"+user.getUsername());
					List<GrantedAuthority> authorities = new ArrayList<>();
					for(GrantedAuthority au : user.getAuthorities()) {
						authorities.add(au);
					}
					
					if(jwtTokenUtil.validateToken(accessToken, user) && SecurityContextHolder.getContext().getAuthentication()==null) {
						UsernamePasswordAuthenticationToken upat  = new UsernamePasswordAuthenticationToken(user,null, authorities);
						upat.setDetails(new WebAuthenticationDetails(request));
						SecurityContextHolder.getContext().setAuthentication(upat);
					}
				}
			}
			filterChain.doFilter(request, response);
		}
		
	}
	
	

}
