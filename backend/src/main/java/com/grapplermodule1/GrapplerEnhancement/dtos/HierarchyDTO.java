package com.grapplermodule1.GrapplerEnhancement.dtos;

import java.util.ArrayList;
import java.util.List;

public class HierarchyDTO {
    private Long id;
    private String name;
    private String designation;
    private List<HierarchyDTO> subordinates = new ArrayList<>();

    public HierarchyDTO() {
    }

    public HierarchyDTO(Long id, String name, String designation, List<HierarchyDTO> subordinates) {
        this.name = name;
        this.designation = designation;
        this.subordinates = subordinates;
    }

    public HierarchyDTO(Long id, String name, String designation) {
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

    public List<HierarchyDTO> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<HierarchyDTO> subordinates) {
        this.subordinates = subordinates;
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
}
