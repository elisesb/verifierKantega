package com.example.verifiserer.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.List;

@Service
public class JwtService {

    //Warning: må fikse
    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username, List<String> roles) {
        // Bruk den genererte nøkkelen for å lage tokenet
        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            System.out.println("Token er gyldig. Bruker: " + claims.getSubject());
            return true;
        } catch (Exception e) {
            System.out.println("Token er ugyldig: " + e.getMessage());
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject(); // Henter "sub" fra token
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}



