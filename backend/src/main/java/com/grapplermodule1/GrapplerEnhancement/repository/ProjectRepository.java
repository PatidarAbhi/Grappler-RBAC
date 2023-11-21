package com.grapplermodule1.GrapplerEnhancement.repository;

import com.grapplermodule1.GrapplerEnhancement.dtos.ProjectDTO;
import com.grapplermodule1.GrapplerEnhancement.entities.Permission;
import com.grapplermodule1.GrapplerEnhancement.entities.Project;
import com.grapplermodule1.GrapplerEnhancement.enums.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsByName(String name);

    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.ProjectDTO(p.id, p.name) FROM Project p ")
    List<ProjectDTO> findListOfProjects();

    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.ProjectDTO(p.id, p.name) FROM Project p WHERE p.id = :projectId")
    Optional<ProjectDTO> findProjectById(Long projectId);

    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.ProjectDTO(p.id, p.name) FROM Project p " +
            "INNER JOIN p.teams t " +
            "INNER JOIN t.teamMembers tm " +
            "INNER JOIN tm.user u " +
            "WHERE u.id = :userId")
    Optional<List<ProjectDTO>> findProjectsByUserId(Long userId);

    @Query("SELECT p FROM Project p JOIN p.teams t WHERE t.id = :teamId")
    List<Project> findProjectsByTeamId(@Param("teamId") Long teamId);

    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.ProjectDTO(p.id, p.name) FROM Project p " +
            "INNER JOIN p.teams t " +
            "INNER JOIN t.teamMembers tm " +
            "INNER JOIN tm.user u " +
            "WHERE u.email = :email")
    Optional<List<ProjectDTO>> findProjectsByUserEmail(String email);



}
