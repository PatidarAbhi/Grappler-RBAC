package com.grapplermodule1.GrapplerEnhancement.dtos;

import com.grapplermodule1.GrapplerEnhancement.validations.PostValidation;
import com.grapplermodule1.GrapplerEnhancement.validations.PutValidation;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {

    @NotEmpty( message = "Old Password is required")
    private String oldPassword;
    @NotEmpty( message = "New Password is required")
    private String newPassword;
    @NotEmpty( message = "Confirm Password is required")
    private String confirmPassword;

}
