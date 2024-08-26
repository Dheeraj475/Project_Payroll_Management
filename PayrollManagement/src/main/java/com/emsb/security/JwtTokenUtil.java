package com.emsb.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.emsb.exception.LoginException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Value("${jwt.token.validity}")
    private long JWT_TOKEN_VALIDITY;

    @Value("${jwt.secret}")
    private String secret;

    // In-memory store for active tokens
    private final Map<String, String> activeTokens = new ConcurrentHashMap<>();

    // Get username from JWT token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Get expiration date from JWT token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Secret key needed
    private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

    // It checks if the token is expired
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // This generates token
    public String generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();

        // Check if there is an active token for this user
        if (activeTokens.containsKey(username) && !isTokenExpired(activeTokens.get(username))) {
            throw new LoginException("User is already logged in with an active session.",activeTokens.get(username));
        }

        Map<String, Object> claims = new HashMap<>();
        String token = doGenerateToken(claims, username);

        // Store the token
        activeTokens.put(username, token);

        return token;
    }

    // Claims of the token like Issuer, Expiration, Subject, and the ID
    // HS512 algorithm is used here to sign the token
    // JWT is compacted using compact() to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    // This validates token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    


    // Method to invalidate token (for logout)
    public void invalidateToken(String token) {
        String username = getUsernameFromToken(token);
        activeTokens.remove(username);
    }
    
    public JwtParser jwtParser() {
        return Jwts.parser()
                   .setSigningKey(secret)
                   .setAllowedClockSkewSeconds(60); // Allow 60 seconds of clock skew
    }

    
    
}
