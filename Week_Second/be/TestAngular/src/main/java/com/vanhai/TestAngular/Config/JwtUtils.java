package com.vanhai.TestAngular.Config;

import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	@org.springframework.beans.factory.annotation.Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@org.springframework.beans.factory.annotation.Value("${app.jwt-expiration-milliseconds}")
	private long jwtExpirationMs;
	
	public String generateToken(org.springframework.security.core.Authentication authentication) {
		String username = authentication.getName();
		
		List<String> roles = authentication.getAuthorities()
							.stream().map(GrantedAuthority::getAuthority)
							.toList();
		
		StringBuilder claimString = new StringBuilder();
		for(String role: roles)
			claimString.append(role);
		
		String claim = claimString.toString().trim();
		
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new java.util.Date())
				.setExpiration(new java.util.Date(new Date().getTime() + jwtExpirationMs))
				.claim("scope", claim)
				.signWith(key())
				.compact();
	}
	
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    
    public String getUserName(String token) {
    	return Jwts.parser()
    			.verifyWith((SecretKey)key())
    			.build()
    			.parseSignedClaims(token)
    			.getPayload()
    			.getSubject();
    }
    
    public boolean validateToken(String token) {
    	try {
    		Jwts.parser()
            .setSigningKey(key())
            .build()
            .parse(token);
    return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
    }

}
