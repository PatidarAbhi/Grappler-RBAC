package com.grapplermodule1.GrapplerEnhancement.entities;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "OtpStore")
@Getter
@Setter
public class OtpStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private Integer otp;

    private String email;
}
