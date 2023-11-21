package com.grapplermodule1.GrapplerEnhancement.controllers;

import com.grapplermodule1.GrapplerEnhancement.cerebrus.jwtauthentication.JwtHelper;
import com.grapplermodule1.GrapplerEnhancement.customexception.CustomResponse;
import com.grapplermodule1.GrapplerEnhancement.customexception.UserNotFoundException;
import com.grapplermodule1.GrapplerEnhancement.entities.JwtRequest;
import com.grapplermodule1.GrapplerEnhancement.entities.JwtResponse;
import com.grapplermodule1.GrapplerEnhancement.entities.Users;
import com.grapplermodule1.GrapplerEnhancement.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtHelper jwtHelper;

    private Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login( @Valid  @RequestBody JwtRequest jwtRequest) {
            String debugUuid = UUID.randomUUID().toString();
            try {
                log.info("Auth Controller Login API Called, UUID {}", debugUuid);
                this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
                UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getEmail());
                String token = jwtHelper.generateToken(userDetails);
                JwtResponse jwtResponse = new JwtResponse(token, userDetails.getUsername());
                return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
            }
            catch (UserNotFoundException e) {
                log.error("UUID {} UserNotFoundException In Auth Controller Login API Exception {}", debugUuid, e.getMessage());
                return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
            }
            catch (Exception e) {
                log.error("UUID {} Exception In Auth Controller Login API Exception {}", debugUuid, e.getMessage());
                return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.BAD_REQUEST);
            }
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid User Name or Password!");
        }
    }
}