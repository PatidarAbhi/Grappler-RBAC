package com.grapplermodule1.GrapplerEnhancement.dtos;

import java.util.ArrayList;
import java.util.List;

public class ImmediateHierarchyDTO {

    private Long id;
    private String name;
    private String designation;
    private List<UsersDTO> subordinates = new ArrayList<>();

    public ImmediateHierarchyDTO(Long id, String name, String designation, List<UsersDTO> subordinates) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.subordinates = subordinates;
    }

    public ImmediateHierarchyDTO() {
    }

    public ImmediateHierarchyDTO(Long id, String name, String designation) {
        this.id = id;
        this.name = name;
        this.designation = designation;
    }

    public Long getId() {
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<UsersDTO> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<UsersDTO> subordinates) {
        this.subordinates = subordinates;
    }
}
