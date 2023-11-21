package com.grapplermodule1.GrapplerEnhancement.dtos;

public class UsersDTO {

    private Long id;
    private String name;
    private String email;
    private String designation;

    public  Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public UsersDTO() {
    }

    public UsersDTO(Long id, String name, String email, String designation) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.designation = designation;

    }
}