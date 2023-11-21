package com.grapplermodule1.GrapplerEnhancement.entities;

import com.grapplermodule1.GrapplerEnhancement.validations.PostValidation;
import com.grapplermodule1.GrapplerEnhancement.validations.PutValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public class JwtRequest {

    @Email(message = "Email should be a valid email address")
    @NotEmpty( message = "Email is required")
    private String email;

    @NotEmpty( message = "Password is required")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
