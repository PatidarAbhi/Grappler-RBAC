package com.grapplermodule1.GrapplerEnhancement.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.grapplermodule1.GrapplerEnhancement.validations.PostValidation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "team_id")
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "Team name is required")
    private String name;

    @ManyToMany
    @JsonIgnore
    private Set<Project> projectSet;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    @JsonBackReference
    @NotNull(groups = {PostValidation.class},message = "Team Members Are Required.")
    private List<TeamMembers> teamMembers;

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

    public List<TeamMembers> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<TeamMembers> teamMembers) {
        this.teamMembers = teamMembers;
    }
}