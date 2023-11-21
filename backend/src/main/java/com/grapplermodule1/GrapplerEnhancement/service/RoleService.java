package com.grapplermodule1.GrapplerEnhancement.service;

import com.grapplermodule1.GrapplerEnhancement.customexception.UserNotFoundException;
import com.grapplermodule1.GrapplerEnhancement.entities.Role;
import com.grapplermodule1.GrapplerEnhancement.entities.Users;
import com.grapplermodule1.GrapplerEnhancement.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    private static final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    /**
     * For Updating Role By User ID
     *
     * @return Role
     */
    public Role updateRole(Long userId, Role role) {
        try {
            log.info("Update Role Service Called");
            Optional<Role> optionalRole = roleRepository.findByUserId(userId);

            if(optionalRole.isPresent()){
                Role userRole = optionalRole.get();

                userRole.setRole(role.getRole());

                Role updatedRole = roleRepository.save(userRole);

                log.info("Update Role Service Returning Updated Role Object");
                return updatedRole;
            }
            else {
                log.error("Update User Details throws UserNotFoundException");
                throw new UserNotFoundException("User Not Found With ID : " + userId);
            }
        } catch (Exception e) {
            log.error("Exception in Update User Details Exception {}", e.getMessage());
            throw e;
        }
    }
}
