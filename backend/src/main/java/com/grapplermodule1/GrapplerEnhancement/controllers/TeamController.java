package com.grapplermodule1.GrapplerEnhancement.controllers;

import com.grapplermodule1.GrapplerEnhancement.customexception.*;
import com.grapplermodule1.GrapplerEnhancement.dtos.ProjectDTO;
import com.grapplermodule1.GrapplerEnhancement.dtos.TeamDTO;
import com.grapplermodule1.GrapplerEnhancement.dtos.TeamMembersDTO;
import com.grapplermodule1.GrapplerEnhancement.entities.Project;
import com.grapplermodule1.GrapplerEnhancement.entities.Team;
import com.grapplermodule1.GrapplerEnhancement.entities.Users;
import com.grapplermodule1.GrapplerEnhancement.repository.TeamRepository;
import com.grapplermodule1.GrapplerEnhancement.service.HierarchyService;
import com.grapplermodule1.GrapplerEnhancement.service.TeamService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teams")
public class TeamController {
    private static final Logger log = LoggerFactory.getLogger(HierarchyService.class);

    @Autowired
    private TeamService teamService;

    /**
     * For Getting Team Data
     *
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<?> getAllTeams(){
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("Get All Teams API Called, UUID {}", debugUuid);
            List<TeamDTO> teams = teamService.getAllTeams();

            log.info("Get All Teams API Returning Teams in ResponseEntity, UUID {}", debugUuid);
            return new ResponseEntity<>(teams, HttpStatus.OK);
        }
        catch (TeamNotFoundException e) {
            log.error("UUID {} TeamNotFoundException In Get All Teams API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            log.error("UUID {} Exception In Get Team By Id API Exception {} ", debugUuid, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * For Getting Team By ID
     *
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{teamId}")
    public ResponseEntity<?> getTeamById(@PathVariable("teamId") Long teamId){
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("Get Team By Id API Called, UUID {}", debugUuid);
            TeamDTO teamDTO = teamService.getTeamById(teamId);

            return new ResponseEntity<>(teamDTO, HttpStatus.OK);
        }
        catch (TeamNotFoundException e) {
            log.error("UUID {} TeamNotFoundException In Get Team By Id API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            log.error("UUID {} Exception In Get Team By Id API Exception {} ", debugUuid, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * For Creating New Team
     *
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> createNewTeam(@Valid @RequestBody Team team){
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("Get Create User API Called, UUID {}", debugUuid);
            Team newTeam = teamService.createTeam(team);
            if (newTeam != null) {
                return new ResponseEntity<>(new CustomResponse<>(true, "Team Created With Id : " + newTeam.getId(),newTeam.getId()), HttpStatus.CREATED);
            }
            else {
                log.error("UUID {} User Not Created", debugUuid);
                return new ResponseEntity<>(new CustomResponse<>(false, "Team Not Created. Please Try Again", null), HttpStatus.BAD_GATEWAY);
            }
        }
        catch (UserNotFoundException e) {
            log.error("UUID {} Exception In Create Team API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (DuplicateTeamName e) {
            log.error("UUID {} Exception In Create Team API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            log.error("UUID {} Exception In Create Team API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * For Deleting Team By ID
     * 
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{teamId}")
    public ResponseEntity<?> deleteTeamById(@PathVariable("teamId") Long teamId){
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("UUID {} Inside Delete Team By Id, User Id {} ", debugUuid, teamId);
            Boolean deleteUser = teamService.deleteTeam(teamId);

            log.info("UUID {} Delete Team By Id Returning Boolean Value True in ResponseEntity ", debugUuid);
            return new ResponseEntity<>(new CustomResponseMessage(deleteUser, "Team Deleted Successfully."), HttpStatus.OK);
        }
        catch (UserNotFoundException e) {
            log.error("UUID {}, TeamNotFoundException in Delete User BY Id API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            log.error("UUID {} Exception In Get User By Id API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * For Updating Team By ID
     * 
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{teamId}")
    public ResponseEntity<?> updateTeamById(@PathVariable("teamId") Long teamId,
                                            @Valid @RequestBody Team team){
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("UUID {} Inside Update Team By Id, User Id {} ", debugUuid, teamId);
            Team updatedTeam = teamService.updateTeamDetails(teamId, team);

            log.info("Update Team By Id Returning User in ResponseEntity, Updated Team Id {} ", updatedTeam.getId());
            return new ResponseEntity<>(new CustomResponseMessage(true, "Team Updation Successful."), HttpStatus.OK);
        }
        catch (TeamNotFoundException e) {
            log.error("UUID {}, TeamNotFoundException in Update User BY Id API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            log.error("UUID {} Exception In Update User By Id API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * For Getting All Projects By Team ID
     *
     * @return ResponseEntity<?>
     */
    @GetMapping("/get-projects/{teamId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllProjects(@PathVariable("teamId") Long teamId){
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("Get All Project API Called, UUID {}", debugUuid);
            List<ProjectDTO> projectDTOList = teamService.getAllProjects(teamId);

            log.info("Get All Project API Returning Teams in ResponseEntity, UUID {}", debugUuid);
            return new ResponseEntity<>(projectDTOList, HttpStatus.OK);
        }
        catch (TeamNotFoundException e) {
            log.error("UUID {} TeamNotFoundException Get All Project API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (ResourseNotFoundException e) {
            log.error("UUID {} ResourseNotFoundException Get All Project API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            log.error("UUID {} Exception In Get All Projects API, Exception {} ", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
