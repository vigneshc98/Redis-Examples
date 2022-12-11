package com.example.demo.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


@Component
public class JwtTokenUtil {
	
	public JwtTokenUtil() {
		
	}

	@Value("${jwt.auth.app}")
	private String appName;
	
	@Value("${jwt.auth.secret_key}")
	private String secretKey;
	
//	@Value("${jwt.auth.expires_in}")
//	private int expiresIn;
	
	Algorithm algorithm ;

	@PostConstruct
	protected void init() {
	 algorithm = Algorithm.HMAC256(secretKey.getBytes());
//	 Algorithm.HMAC256("secretkey@feeM1234".getBytes());
	}

	public DecodedJWT getAllClaimsFromToken(String token) {
		JWTVerifier verifier =  JWT.require(algorithm).build();
		DecodedJWT decodedJWT = verifier.verify(token);
		return decodedJWT;
    }
	
	 public String getUsernameFromToken(String token) {
			JWTVerifier verifier =  JWT.require(algorithm).build();
			DecodedJWT decodedJWT = verifier.verify(token);
			String username = decodedJWT.getSubject();
	        return username;
	 }
	 
	 public String[] getAuthsFromToken(String token) throws Exception {
		 JWTVerifier verifier =  JWT.require(algorithm).build();
		 DecodedJWT decodedJWT = verifier.verify(token);
		 String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
		 return roles;
	 }
	 
	 public String generateAccessToken(User user) throws InvalidKeySpecException, NoSuchAlgorithmException {
	        
			String access_token = JWT.create()
					.withSubject(user.getUsername())
					.withIssuedAt(new Date(System.currentTimeMillis()))
					.withExpiresAt(new Date(System.currentTimeMillis() + 15 *60 * 1000))
					.withIssuer(appName)
					.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//					.withClaim("roles", user.getAuthorities())
					.sign(algorithm);
//			System.out.println("INSIDE JWT:"+user.getUsername()+" ACCESS_TOKEN:"+access_token);
			return access_token;
	  }
	 
	 public String generateRefreshToken(User user) throws InvalidKeySpecException, NoSuchAlgorithmException {
	        
			String refresh_token = JWT.create()
					.withSubject(user.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + 20 * 60 * 1000))
					.withIssuer(appName)
					.sign(algorithm);
			
			return refresh_token;
	  }
	 
	 public Boolean validateToken(String token, User user) {
	        final String username = getUsernameFromToken(token);
	        return (
	                username != null &&
	                username.equals(user.getUsername()) &&
	                        !isTokenExpired(token)
	        );
	  }
	 
	 public boolean isTokenExpired(String token) {
		Date expireDate=getExpirationDate(token);
		return expireDate.before(new Date());
	}
	 
	public Date getExpirationDate(String token) {
		 Date expireDate;
		     try {
		            final DecodedJWT decodeJwt = getAllClaimsFromToken(token);
		            expireDate = decodeJwt.getExpiresAt();
		      } catch (Exception e) {
		        expireDate = null;
		      }
		    return expireDate;
	}
	 
	public Date getIssuedAtDateFromToken(String token) {
	       Date issueAt;
	       try {
	           final DecodedJWT decodeJwt = getAllClaimsFromToken(token);
	           issueAt = decodeJwt.getIssuedAt();
	       } catch (Exception e) {
	           issueAt = null;
	       }
	       return issueAt;
	  }
	
	public String getToken( HttpServletRequest request ) {
	      
	       String authHeader = request.getHeader("Authorization");
	       if ( authHeader != null && authHeader.startsWith("Bearer ")) {
	           return authHeader.substring(7);
	       }
	       return null;
	  }
	 
}
