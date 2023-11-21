package com.grapplermodule1.GrapplerEnhancement.service;

import com.grapplermodule1.GrapplerEnhancement.customexception.DuplicateTeamName;
import com.grapplermodule1.GrapplerEnhancement.customexception.ResourseNotFoundException;
import com.grapplermodule1.GrapplerEnhancement.customexception.TeamNotFoundException;
import com.grapplermodule1.GrapplerEnhancement.customexception.UserNotFoundException;
import com.grapplermodule1.GrapplerEnhancement.dtos.ProjectDTO;
import com.grapplermodule1.GrapplerEnhancement.dtos.TeamDTO;
import com.grapplermodule1.GrapplerEnhancement.dtos.TeamMembersDTO;
import com.grapplermodule1.GrapplerEnhancement.dtos.UsersDTO;
import com.grapplermodule1.GrapplerEnhancement.entities.Project;
import com.grapplermodule1.GrapplerEnhancement.entities.Team;
import com.grapplermodule1.GrapplerEnhancement.entities.TeamMembers;
import com.grapplermodule1.GrapplerEnhancement.entities.Users;
import com.grapplermodule1.GrapplerEnhancement.repository.ProjectRepository;
import com.grapplermodule1.GrapplerEnhancement.repository.TeamMemberRepository;
import com.grapplermodule1.GrapplerEnhancement.repository.TeamRepository;
import com.grapplermodule1.GrapplerEnhancement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {
    private static final Logger log = LoggerFactory.getLogger(HierarchyService.class);
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private HierarchyService hierarchyService;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    /**
     * For Getting All The Teams
     *
     * @return List<Team>
     */
    public List<TeamDTO> getAllTeams() {
        try {
            log.info("Get All Teams Service Called");
            List<TeamDTO> teamDTOList = teamRepository.findListOfTeams();

            if(!teamDTOList.isEmpty()){

                teamDTOList.forEach(teamDTO -> {
                    teamDTO.setTeamMembers(getMembersList(teamDTO.getId()));
                });

                return teamDTOList;
            }
            else {
                log.error("Get All Teams Service Call TeamNotFoundException");
                throw new TeamNotFoundException("Teams List Not Found.");
            }
        }
        catch (Exception e) {
            log.error("Exception In Get All Teams Exception {}", e.getMessage());
            throw  e;
        }
    }

    /**
     * For Getting List Of Members
     *
     * @return List<TeamMembersDTO>
     */
    public List<TeamMembersDTO> getMembersList(Long teamId) {
        try {
            log.info("Get Team Member By Id Called in Hierarchy Service");
            Optional<List<TeamMembersDTO>> optionalListOfUsers = teamMemberRepository.findUsersByTeamId(teamId);

            if(optionalListOfUsers.isPresent() && !optionalListOfUsers.get().isEmpty()){
                log.info("Get Team Member By Id returning List of TeamMemberDTO");
                return optionalListOfUsers.get();
            }
            else {
                return optionalListOfUsers.get();
            }
        }
        catch (Exception e) {
            log.error("Exception In Get Members List Service in Hiearachy Service Exception {}", e.getMessage());
            throw e;
        }
    }

    /**
     * For Get Team By ID
     *
     * @return TeamDTO
     */
    public TeamDTO getTeamById(Long teamId) {
        try {
            log.info("Get Team By Id Service Called");
            Optional<TeamDTO> teamDTO = teamRepository.findTeamById(teamId);

            if(teamDTO.isPresent()){
                teamDTO.get().setTeamMembers(getMembersList(teamId));

                return teamDTO.get();
            }
            else {
                log.error("Get All Teams Service Call TeamNotFoundException");
                throw new TeamNotFoundException("Team Not Found With Id : " + teamId);
            }
        }
        catch (Exception e) {
            log.error("Exception In Get All Teams Exception {}", e.getMessage());
            throw  e;
        }
    }

    /**
     * For Creating A New Team
     * 
     * @return Team
     */
    public Team createTeam(Team team) {
        try {
            log.info("Create Team Service Called");

            Optional<Team> optionalTeam = teamRepository.findByName(team.getName());

            if(optionalTeam.isPresent()){
                log.error("Create Teams throws DuplicateTeamNameException");
                throw new DuplicateTeamName("Team With Name " + team.getName() + " Is Already Present.");
            }

            List<TeamMembers> teamMembersList = team.getTeamMembers();

            teamMembersList.forEach(eachTeam -> {
                Optional<UsersDTO> userDTO = userRepository.findUserDtoById(eachTeam.getUser().getId());
                if(userDTO.isEmpty()) {
                    throw new UserNotFoundException("User Not Found With ID : " + eachTeam.getUser().getId());
                }
            });

            teamMembersList.forEach(teamMembers -> {
                teamMembers.setTeam(team);
            });

            return teamRepository.save(team);
        }
        catch (Exception e) {
            log.error("Exception In Create Teams Exception {}", e.getMessage());
            throw e;
        }
    }

    /**
     * For Delete Team By ID
     *
     * @return Boolean
     */
    public Boolean deleteTeam(Long teamId) {
        try {
            log.info("Delete Team Service Called, User Id {}", teamId);
            Optional<Team> team = teamRepository.findById(teamId);

            if(team.isPresent()){
                //teamRepository.removeTeamFromProjects(team.get());
                List<Project> projects=projectRepository.findProjectsByTeamId(teamId);
                for (Project project : projects) {
                    project.getTeams().remove(team.get());
                    projectRepository.save(project);
                }
                teamRepository.deleteTeamMembersByTeam(team.get());
                teamRepository.deleteTeamById(teamId);
               // teamRepository.deleteById(teamId);
                log.info("Delete Team Service Returning True Boolean Value");
                return true;
            }
            else {
                log.error("Delete Team throws TeamNotFoundException");
                throw new TeamNotFoundException("Team Not Found With ID : " + teamId);
            }
        }
        catch (Exception e) {
            log.error("Exception in Delete Team Exception {}", e.getMessage());
            throw e;
        }
    }

    /**
     * For Updating Team Details
     * 
     * @return Team
     */
    public Team updateTeamDetails(Long teamId, Team team) {
        try {
            log.info("Update Team Details Service Called, User Id {}", teamId);
            Optional<Team> existingOptionalTeam = teamRepository.findById(teamId);

            if(existingOptionalTeam.isPresent()){
                Team existingTeam = existingOptionalTeam.get();

                existingTeam.setName(team.getName());

                Team updatedTeam = teamRepository.save(existingTeam);

                log.info("Update Team Details Service Returning Updated Team");
                return updatedTeam;
            }
            else {
                log.error("Update Team Details throws TeamNotFoundException");
                throw new TeamNotFoundException("Team Not Found With ID : " + teamId);
            }
        } catch (Exception e) {
            log.error("Exception in Update User Details Exception {}", e.getMessage());
            throw e;
        }
    }

    public List<ProjectDTO> getAllProjects(Long teamId){
        try {
            log.info("Get Team By Id Service Called");
            Optional<Team> optionalTeam = teamRepository.findById(teamId);
            if(optionalTeam.isPresent()){
                Optional<List<ProjectDTO>> projectDTO =teamRepository.findProjectsByTeamId(teamId);
                if(projectDTO.isPresent()){
                    return projectDTO.get();
                }
                else {
                    throw new ResourseNotFoundException("Team List Not Found.");
                }
            }
            else {
                log.error("TeamNotFoundException in Get All Projects Details");
                throw new TeamNotFoundException("Team Not Found With Id : " + teamId);
            }

        }
        catch (Exception e) {
            log.error("Exception In Get All Teams Exception {}", e.getMessage());
            throw  e;
        }
    }
}
