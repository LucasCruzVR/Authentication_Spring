package com.castgroup.auth.api.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.castgroup.auth.api.config.JwtConfig;
import com.castgroup.auth.api.model.UserAuthReqDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter  extends UsernamePasswordAuthenticationFilter{

    private JwtConfig jwtConfig;
    private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter (AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
		this.authenticationManager = authenticationManager;
		this.jwtConfig = jwtConfig;
	}

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp ){
        try {
        var request = new ObjectMapper().readTree(req.getInputStream());
        UserAuthReqDTO user = new UserAuthReqDTO();
        user.setEmail(request.get("email").asText());
        user.setPassword(request.get("password").asText());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>());
        return authenticationManager.authenticate(authToken);
        } catch(IOException e) {
            throw new NullPointerException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp, FilterChain chain, Authentication auth) throws IOException, ServletException{
        String email = ((UserAuthReqDTO) auth.getPrincipal()).getUsername();
        String token = jwtConfig.generateToken(email);
        resp.addHeader("Authorization", "Bearer " + token);
    }
}


