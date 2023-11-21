package com.grapplermodule1.GrapplerEnhancement.controllers;

import com.grapplermodule1.GrapplerEnhancement.customexception.CustomResponse;
import com.grapplermodule1.GrapplerEnhancement.customexception.UserNotFoundException;
import com.grapplermodule1.GrapplerEnhancement.dtos.UsersDTO;
import com.grapplermodule1.GrapplerEnhancement.entities.Users;
import com.grapplermodule1.GrapplerEnhancement.service.UserService;
import com.grapplermodule1.GrapplerEnhancement.validations.PostValidation;
import com.grapplermodule1.GrapplerEnhancement.validations.PutValidation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class InvalidUrlExceptionHandlerController {
    private static final Logger log = LoggerFactory.getLogger(InvalidUrlExceptionHandlerController.class);

    /**
     * For Handling Invalid URL
     * 
     * @return ResponseEntity<CustomResponse<String>>
     */
//    @RequestMapping("/**")
//    public ResponseEntity<CustomResponse<String>> handleInvalidUrl() {
//        String debugUuid = UUID.randomUUID().toString();
//        log.info("InvalidUrlExceptionHandlerController handleInvalidUrl API Called, UUID {}", debugUuid);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(false, "Invalid URL requested", null));
//    }

}