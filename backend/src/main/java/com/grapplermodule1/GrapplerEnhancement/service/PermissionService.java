package com.grapplermodule1.GrapplerEnhancement.service;

import com.grapplermodule1.GrapplerEnhancement.customexception.CustomPermissionException;
import com.grapplermodule1.GrapplerEnhancement.customexception.ProjectNotFoundException;
import com.grapplermodule1.GrapplerEnhancement.customexception.UserNotFoundException;
import com.grapplermodule1.GrapplerEnhancement.entities.Project;
import com.grapplermodule1.GrapplerEnhancement.entities.TeamMembers;
import com.grapplermodule1.GrapplerEnhancement.enums.PermissionType;
import com.grapplermodule1.GrapplerEnhancement.repository.PermissionRepository;
import com.grapplermodule1.GrapplerEnhancement.repository.ProjectRepository;
import com.grapplermodule1.GrapplerEnhancement.repository.TeamMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PermissionService {

    private static final Logger log = LoggerFactory.getLogger(PermissionService.class);

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private ProjectRepository projectRepository;


    /**
     * For Getting Permissions Of Member In A Project
     *
     * @return PermissionType
     */
    public PermissionType getMemberPermission(Long memberId, Long projectId) {
        try {
            log.info("Fetch Member Permission By memberId and projectId Service Called, memberId {} Id and projectId {}", memberId, projectId);

            Optional<TeamMembers> teamMembers = teamMemberRepository.findById(memberId);
            if (teamMembers.isEmpty()) {
                log.error("Fetch Member Permission By memberId and projectId Service throws UserNotFoundException");
                throw new UserNotFoundException("Member Not Found With Id :" + memberId);
            }
            Optional<Project> project = projectRepository.findById(projectId);
            if (project.isEmpty()) {
                log.error("Fetch Member Permission By memberId and projectId Service throws ProjectNotFoundException");
                throw new ProjectNotFoundException("Project Not Found With Id :" + projectId);
            }

            PermissionType permissionType = permissionRepository.findPermissionTypeByMemberIdAndProjectId(memberId, projectId);
            if (permissionType != null) {
                log.info("getMemberPermission Service Returning permissionType");
                return permissionType;
            } else {
                log.error("Fetch Member Permission By memberId and projectId Service throws CustomPermissionException");
                throw new CustomPermissionException("Member With Id :" + memberId + " Is Not Associated With  Project Id :" + projectId);
            }
        } catch (Exception e) {
            log.error("Fetch Member Permission By memberId and projectId Exception {}", e.getMessage());
            throw e;
        }
    }

    /**
     * For Updating Permissions Of Member In A Project
     *
     * @return int
     */
    public int updatePermissionToMember(Long memberId, Long projectId,
                                     PermissionType permissionType) {
        try {
            log.info("Update Member Permission By memberId and projectId Service Called, member {} Id and projectId {}", memberId, projectId);
            Optional<TeamMembers> teamMembers = teamMemberRepository.findById(memberId);
            if (teamMembers.isEmpty()) {
                log.error("Update Member Permission By memberId and projectId Service throws UserNotFoundException");
                throw new UserNotFoundException("Member Not Found With Id :" + memberId);
            }
            Optional<Project> project = projectRepository.findById(projectId);
            if (project.isEmpty()) {
                log.error("Update Member Permission By memberId and projectId Service throws ProjectNotFoundException");
                throw new ProjectNotFoundException("Project Not Found With Id :" + projectId);
            }
            int updateStatus = permissionRepository.addPermissionTypeByMemberIdAndProjectId(memberId, projectId, permissionType);
            if (updateStatus > 0) {
                log.info("Update Member Permission Service Returning Update Status");
                return updateStatus;
            } else {
                log.error("Update Member Permission By memberId and projectId Service throws CustomPermissionException");
                throw new CustomPermissionException("Member With Id :" + memberId + " Is Not Associated With  Project Id :" + projectId);
            }
        } catch (Exception e) {
            log.error("Update Member Permission By memberId and projectId Exception {}", e.getMessage());
            throw e;
        }

    }


}
