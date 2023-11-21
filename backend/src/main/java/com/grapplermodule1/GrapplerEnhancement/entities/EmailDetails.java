package com.grapplermodule1.GrapplerEnhancement.entities;

import com.grapplermodule1.GrapplerEnhancement.validations.PostValidation;
import com.grapplermodule1.GrapplerEnhancement.validations.PutValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails {

    @Email(groups = {PostValidation.class, PutValidation.class}, message = "Email should be a valid email address")
    @NotEmpty(groups = {PostValidation.class, PutValidation.class}, message = "Email is required")
    @Size(max = 255, message = "Email should not exceed 255 characters")
    private String email;



}
