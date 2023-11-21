package com.grapplermodule1.GrapplerEnhancement.dtos;

import java.util.List;

public class ProjectDTO {
    private Long id;
    private String name;
    private List<TeamDTO> teams;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<TeamDTO> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamDTO> teams) {
        this.teams = teams;
    }

    public ProjectDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
