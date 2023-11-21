package com.grapplermodule1.GrapplerEnhancement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grapplermodule1.GrapplerEnhancement.enums.AccessLevel;
import com.grapplermodule1.GrapplerEnhancement.enums.PermissionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "permission_id")
    private Long id;

    @Column(name = "permission_type")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Permission type is required")
    private PermissionType permission_type;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "member_id")
    private TeamMembers teamMembers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PermissionType getPermission_type() {
        return permission_type;
    }

    public void setPermission_type(PermissionType permission_type) {
        this.permission_type = permission_type;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public TeamMembers getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(TeamMembers teamMembers) {
        this.teamMembers = teamMembers;
    }
}
