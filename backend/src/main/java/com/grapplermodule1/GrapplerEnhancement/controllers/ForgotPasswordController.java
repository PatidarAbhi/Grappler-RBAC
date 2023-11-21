package com.grapplermodule1.GrapplerEnhancement.controllers;

import com.grapplermodule1.GrapplerEnhancement.customexception.CustomResponseMessage;
import com.grapplermodule1.GrapplerEnhancement.customexception.PasswordNotMatchException;
import com.grapplermodule1.GrapplerEnhancement.customexception.UserNotFoundException;
import com.grapplermodule1.GrapplerEnhancement.entities.*;
import com.grapplermodule1.GrapplerEnhancement.repository.OtpRepository;
import com.grapplermodule1.GrapplerEnhancement.repository.UserRepository;
import com.grapplermodule1.GrapplerEnhancement.service.EmailService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Random;

@RequestMapping("/forgot-password")
@RestController
public class ForgotPasswordController {

    private Logger log = LoggerFactory.getLogger(ForgotPasswordController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    /**
     * For Sending Otp
     *
     * @return ResponseEntity
     */
    @PostMapping("/send-Password")
    public ResponseEntity<?> sendPassword(@Valid @RequestBody EmailDetails emailDetails) {
        try {
                    log.info("ForgotPassword Controller Send Password On Email API Called {}", emailDetails.getEmail());
            Optional<Users> user = userRepository.findByEmail(emailDetails.getEmail());
            if (user.isPresent()) {
                Boolean status = emailService.sendSimpleMail(emailDetails.getEmail());
                log.info("Send Password On Email  Returning Status {}", status);
                return new ResponseEntity<>(new CustomResponseMessage(status, "Reset password link sent to email"), HttpStatus.OK);
            } else {
                log.error("User not found for email: {}", emailDetails.getEmail());
                return new ResponseEntity<>(new CustomResponseMessage(false, "User not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Exception in Send Password On Email  API: {}", e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, "Failed to send email"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
