package com.grapplermodule1.GrapplerEnhancement.dtos;

import java.util.List;

public class TeamDTO {
    private Long id;
    private String name;
    private List<TeamMembersDTO> teamMembers;

    public TeamDTO() {
    }

    public TeamDTO(Long id, String name, List<TeamMembersDTO> teamMembers) {
        this.id = id;
        this.name = name;
        this.teamMembers = teamMembers;
    }

    public TeamDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public List<TeamMembersDTO> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<TeamMembersDTO> teamMembers) {
        this.teamMembers = teamMembers;
    }
}
