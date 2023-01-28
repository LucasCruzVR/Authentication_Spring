package com.castgroup.auth.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.castgroup.auth.api.config.JwtConfig;
import com.castgroup.auth.api.domain.User;
import com.castgroup.auth.api.model.UserAuthReqDTO;
import com.castgroup.auth.api.model.UserAuthRespDTO;
import com.castgroup.auth.api.model.UserRespDTO;
import com.castgroup.auth.api.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authManager;
    
    @PostMapping
    @ResponseBody
    public ResponseEntity<UserRespDTO> createUser(@RequestBody @Validated User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.saveUser(user));
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody UserAuthReqDTO userReq) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        userReq.getEmail(), userReq.getPassword())
            );
            UserAuthReqDTO user = (UserAuthReqDTO) authentication.getPrincipal();
            return ResponseEntity.ok().body(authenticationService.authenticationToken(user));
             
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
