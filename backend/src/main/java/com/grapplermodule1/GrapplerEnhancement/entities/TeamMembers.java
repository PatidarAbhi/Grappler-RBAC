package com.grapplermodule1.GrapplerEnhancement.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "team_members")
public class TeamMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "member_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonIgnore
    @JsonManagedReference
    private Team team;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

//    @ManyToMany(cascade = CascadeType.ALL)
//    private  List<Ticket> tickets;

    public TeamMembers() {
    }

    public TeamMembers(Team team, Users user) {
        this.team = team;
        this.user = user;
    }

    public TeamMembers(Long id, Team team, Users user, List<Permission> permission) {
        this.id = id;
        this.team = team;
        this.user = user;
        this.permission = permission;
    }

    @OneToMany(mappedBy = "teamMembers", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Permission> permission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Permission> getPermission() {
        return permission;
    }

    public void setPermission(List<Permission> permission) {
        this.permission = permission;
    }

//    public List<Ticket> getTickets() {
//        return tickets;
//    }
//
//    public void setTickets(List<Ticket> tickets) {
//        this.tickets = tickets;
//    }
}