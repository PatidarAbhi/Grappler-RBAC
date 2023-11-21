package com.grapplermodule1.GrapplerEnhancement.repository;

import com.grapplermodule1.GrapplerEnhancement.dtos.ProjectDTO;
import com.grapplermodule1.GrapplerEnhancement.dtos.TeamDTO;
import com.grapplermodule1.GrapplerEnhancement.entities.Team;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TeamRepository extends JpaRepository<Team, Long> {

    @org.springframework.transaction.annotation.Transactional
    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.TeamDTO(t.id, t.name) FROM Team t")
    List<TeamDTO> findListOfTeams();

    @org.springframework.transaction.annotation.Transactional
    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.TeamDTO(t.id, t.name) FROM Team t WHERE t.id = :teamId")
    Optional<TeamDTO> findTeamById(Long teamId);

    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.TeamDTO(t.id, t.name) FROM Project p JOIN p.teams t WHERE p.id = :projectId")
    List<TeamDTO> searchTeamById(Long projectId);

    Optional<Team> findByName(String name);

    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.TeamDTO(t.id, t.name) FROM Team t " +
            "INNER JOIN t.teamMembers tm " +
            "INNER JOIN tm.user u " +
            "WHERE u.id = :userId")
    Optional<List<TeamDTO>> findTeamsByUserId(Long userId);

    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.ProjectDTO(p.id, p.name) FROM Project p JOIN p.teams t WHERE t.id = :teamId")
    Optional<List<ProjectDTO>> findProjectsByTeamId(Long teamId);

    @org.springframework.transaction.annotation.Transactional
    @Modifying
    @Query("DELETE FROM TeamMembers t WHERE t.team = :team")
    void deleteTeamMembersByTeam(@Param("team") Team team);

    @org.springframework.transaction.annotation.Transactional
    @Modifying
    @Query("DELETE FROM Team t WHERE t.id = :teamId")
    void deleteTeamById(@Param("teamId") Long teamId);

}
