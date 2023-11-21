package com.grapplermodule1.GrapplerEnhancement.entities;

import com.grapplermodule1.GrapplerEnhancement.validations.PostValidation;
import com.grapplermodule1.GrapplerEnhancement.validations.PutValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPassword {

    @NotNull(message = "New Password Is Required")
    private String newPassword;

    @NotNull(message = "Confirm Password Is Required")
    private String confirmPassword;

    @Email( message = "Email should be a valid email address")
    @NotEmpty( message = "Email is required")
    @Size(max = 255, message = "Email should not exceed 255 characters")
    private  String  email;


}
