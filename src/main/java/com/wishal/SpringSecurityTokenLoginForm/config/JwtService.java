package com.wishal.SpringSecurityTokenLoginForm.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

@Service
public class JwtService {

    private  static final String SECRET_KEY="5e3105ab73d8c46906a079e52f98449f32283e7b0cf4d6f9746f71dd53713b49";
    public String extractUsername(String token) {
        return  extractClaim(token,Claims::getSubject);
    }
    private Claims extractAllClaims (String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim (String token, Function<Claims,T>claimsResolver ){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
