package com.grapplermodule1.GrapplerEnhancement.controllers;

import com.grapplermodule1.GrapplerEnhancement.customexception.*;
import com.grapplermodule1.GrapplerEnhancement.entities.Permission;
import com.grapplermodule1.GrapplerEnhancement.enums.PermissionType;
import com.grapplermodule1.GrapplerEnhancement.service.PermissionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    private static final Logger log = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private PermissionService permissionService;


    /**
     * For Getting Permissions Of Member In A Project
     *
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{memberId}/project-permission/{projectId}")
    public ResponseEntity<?> getMemberPermission
    (@PathVariable("memberId") Long memberId,
     @PathVariable("projectId") Long projectId) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("UUID {} getMemberPermission By memberId and projectId, memberId {} ", debugUuid, memberId);
            PermissionType permissionType = permissionService.getMemberPermission(memberId, projectId);
            log.info("Get getMemberPermission By memberId and projectId Returning Permission in ResponseEntity, memberId {} ", memberId);
            return new ResponseEntity<>(permissionType, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            log.error("UUID {}, UserNotFoundException in getMemberPermission API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        } catch (ProjectNotFoundException e) {
            log.error("UUID {}, ProjectNotFoundException in getMemberPermission API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        } catch (CustomPermissionException e) {
            log.error("UUID {}, CustomPermissionException in getMemberPermission API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("UUID {} Exception In getMemberPermission API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * For Updating Permissions Of Member In A Project
     *
     * @return ResponseEntity
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{memberId}/update-permissions/{projectId}")
    public ResponseEntity updatePermissionToMember(@PathVariable("memberId") Long memberId,
                                                   @PathVariable("projectId") Long projectId,
                                                   @Valid @RequestBody Permission permission) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("UUID {} updatePermissionToMember By memberId and projectId, memberId {} ", debugUuid, memberId);
            int updateStatus = permissionService.updatePermissionToMember(memberId, projectId, permission.getPermission_type());
            log.info("Get updatePermissionToMember By memberId and projectId Returning Update status in ResponseEntity, memberId {} ", memberId);
            return new ResponseEntity<>(new CustomResponseMessage(true, "Permission Updated successfully"), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            log.error("UUID {}, UserNotFoundException in updatePermissionToMember API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        } catch (ProjectNotFoundException e) {
            log.error("UUID {}, ProjectNotFoundException in updatePermissionToMember API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        } catch (CustomPermissionException e) {
            log.error("UUID {}, CustomPermissionException in updatePermissionToMember API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("UUID {} Exception In updatePermissionToMember API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
