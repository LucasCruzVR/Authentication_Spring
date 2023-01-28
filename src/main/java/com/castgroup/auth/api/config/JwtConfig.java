package com.castgroup.auth.api.config;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtConfig {
    
    private final String jwtSecret= "EE2@09BDD3EE8E$4&2B3889612BC4E6F7#412$";
    private final Integer expiration= 60000;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

}
