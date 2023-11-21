package com.grapplermodule1.GrapplerEnhancement.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "project_id")
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "Project name is required")
    private String name;

    @ManyToMany
    @JsonBackReference
    @JoinTable(name = "project_team",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private Set<Team> teams;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
    private List<Permission> permission;


//    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
//    private List<Ticket> ticket;

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

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

}
