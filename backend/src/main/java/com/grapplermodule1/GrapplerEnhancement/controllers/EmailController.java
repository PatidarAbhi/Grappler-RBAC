package com.grapplermodule1.GrapplerEnhancement.controllers;

import com.grapplermodule1.GrapplerEnhancement.customexception.CustomResponse;
import com.grapplermodule1.GrapplerEnhancement.customexception.CustomResponseMessage;
import com.grapplermodule1.GrapplerEnhancement.customexception.UserNotFoundException;
import com.grapplermodule1.GrapplerEnhancement.entities.EmailDetails;
import com.grapplermodule1.GrapplerEnhancement.entities.JwtRequest;
import com.grapplermodule1.GrapplerEnhancement.entities.JwtResponse;
import com.grapplermodule1.GrapplerEnhancement.service.EmailService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/sendMail")
public class EmailController {

    private Logger log = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;

    /**
     * For Sending Mail
     *
     * @return  ResponseEntity
     */
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> sendMail( @Valid @RequestBody EmailDetails emailDetails) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("Email Controller Send Mail API Called, UUID {}", debugUuid);
            Boolean status = emailService.sendSimpleMail("emailDetail");
            return new ResponseEntity<>(new CustomResponseMessage(status, "Mail Sent Successfully"),HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("UUID {} Exception In Email Controller Send Mail API Exception {}", debugUuid, e.getMessage());
            throw e;
        }
    }
}
