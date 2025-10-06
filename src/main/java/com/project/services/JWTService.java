package com.project.services;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

	private String secretKey="";
	
	public JWTService() throws NoSuchAlgorithmException {
		KeyGenerator KeyGen =KeyGenerator.getInstance("HmacSHA256");
		SecretKey sk =KeyGen.generateKey();
		this.secretKey =  Base64.getEncoder().encodeToString(sk.getEncoded());
	}
	
	public String generateToken(String email) {
		Map<String, Object> claims=new HashMap<>();
		
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(email)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+1000* 60*60))
				.and()
				.signWith(getKey())
				.compact();
				
	}

	private Key getKey() {

		byte[] keyBytes =Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUserName(String token) {

		return Jwts.parser()
                .verifyWith((SecretKey)getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
	    final String username = extractUserName(token);
	    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
	    Date expiration = Jwts.parser()
	    		.verifyWith((SecretKey) getKey())
	    		.build()
	            .parseSignedClaims(token)
	            .getPayload()
	            .getExpiration();
	    return expiration.before(new Date());
	}

}