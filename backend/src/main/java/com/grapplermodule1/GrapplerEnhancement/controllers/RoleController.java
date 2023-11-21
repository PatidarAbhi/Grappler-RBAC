package com.grapplermodule1.GrapplerEnhancement.controllers;

import com.grapplermodule1.GrapplerEnhancement.customexception.CustomResponseMessage;
import com.grapplermodule1.GrapplerEnhancement.customexception.UserNotFoundException;
import com.grapplermodule1.GrapplerEnhancement.entities.Role;
import com.grapplermodule1.GrapplerEnhancement.entities.Users;
import com.grapplermodule1.GrapplerEnhancement.service.RoleService;
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
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    /**
     * For Updating Role By User ID
     * 
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserRole(@PathVariable("userId") Long userId, @Valid @RequestBody Role role) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("UUID {} Inside Update Role, UUID {} ", debugUuid, userId);
            Role updateRole = roleService.updateRole(userId, role);

            log.info("Update Role Returning Role in ResponseEntity, Updated Role Id {} ", updateRole.getId());
            return new ResponseEntity<>(new CustomResponseMessage(true, "Role With ID " + updateRole.getId() + " Updated Successfully."), HttpStatus.OK);
        }
        catch (UserNotFoundException e) {
            log.error("UUID {}, UserNotFoundException in Update Role API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            log.error("UUID {} Exception In Update Role API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
