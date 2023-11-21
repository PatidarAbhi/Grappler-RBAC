package com.grapplermodule1.GrapplerEnhancement.service;

import com.grapplermodule1.GrapplerEnhancement.cerebrus.config.UserDetailsConfig;
import com.grapplermodule1.GrapplerEnhancement.customexception.*;
import com.grapplermodule1.GrapplerEnhancement.dtos.*;
import com.grapplermodule1.GrapplerEnhancement.entities.Role;
import com.grapplermodule1.GrapplerEnhancement.entities.Users;
import com.grapplermodule1.GrapplerEnhancement.repository.ProjectRepository;
import com.grapplermodule1.GrapplerEnhancement.repository.TeamRepository;
//import com.grapplermodule1.GrapplerEnhancement.repository.TicketRepository;
import com.grapplermodule1.GrapplerEnhancement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;

@Service("userService")
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ProjectRepository projectRepository;

//    @Autowired
//    TicketRepository ticketRepository;



    @Autowired
    TeamRepository teamRepository;

    @Autowired
    CustomExceptionHandler customExceptionHandler;

    private static final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return new UserDetailsConfig(optionalUser.get());
        } else {
            throw new UsernameNotFoundException("Could not find user!!");
        }
    }

    /**
     * For Fetching All Users
     *
     * @return List<UsersDTO>
     */
    public List<UsersDTO> fetchAllUsers() {
        try {
            log.info("Fetch All Users Service Called");
            Optional<List<UsersDTO>> usersDTOS = userRepository.findAllUser();
            if (usersDTOS.isPresent()) {
                log.info("Fetch All Users Service Returning UserDTO");
                return usersDTOS.get();
            } else {
                log.error("Fetch All Users Service Call UserNotFoundException");
                throw new UserNotFoundException("Users Not found.");
            }
        } catch (Exception e) {
            log.info("Exception In Fetch All Users Exception {}", e.getMessage());
            throw e;
        }
    }


    public Page<UsersDTO> fetchAllUsersByPagination(Pageable pageable) {
        try {
            log.info("Fetch All Users Service Called");
            Page<UsersDTO> usersPage = userRepository.findAllUserByPagination(pageable);
            if (usersPage.hasContent()) {
                log.info("Fetch All Users Service Returning UserDTO");
                return usersPage;
            } else {
                log.error("Fetch All Users Service Call UserNotFoundException");
                throw new UserNotFoundException("Users Not found.");
            }
        } catch (Exception e) {
            log.info("Exception In Fetch All Users Exception {}", e.getMessage());
            throw e;
        }
    }


    /**
     * For Adding A New User
     *
     * @return Users
     */
    public UsersDTO addUser(Users user) {
        try {
            log.info("Add New User Service Called");
            Optional<Users> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                throw new DuplicateEmailException("A user with the email " + (user.getEmail() + " already exists"));
            }
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<Role>> violations = validator.validate(user.getRole());

            if (!violations.isEmpty()) {
                String errorMessage = "";
                for (ConstraintViolation<Role> violation : violations) {
                    errorMessage += violation.getMessage();
                }
                throw new ValidationException(errorMessage);
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            Role role = new Role();
            role.setRole(user.getRole().getRole());

            user.setRole(role);
            role.setUser(user);

            Users newUser = userRepository.save(user);
            Optional<UsersDTO> usersDTO=userRepository.findUserDtoById(newUser.getId());
            log.info("Add New User Service Returning New User");
            return usersDTO.get();
        } catch (DataIntegrityViolationException e) {
            log.error("DataIntegrityViolationException in Create User Exception {}", e.getMessage());
            throw new DataIntegrityViolationCustomException("User creation failed. Please check the provided information.");
        } catch (Exception e) {
            log.error("Exception In Add User Service Exception {}", e.getMessage());
            throw e;
        }
    }

    /**
     * For Fetching User By ID
     *
     * @return UsersDTO
     */
    public UsersDTO fetchUserById(Long userId) {
        try {
            log.info("Fetch User By ID Service Called, User Id {}", userId);
            Optional<UsersDTO> usersDTO = userRepository.findUserDtoById(userId);
            if (usersDTO.isPresent()) {
                log.info("Fetch User By ID Service Returning UsersDTO");
                return usersDTO.get();
            } else {
                log.error("Fetch User By Id throws UserNotFoundException");
                throw new UserNotFoundException("User Not Found With ID : " + userId);
            }
        } catch (Exception e) {
            log.error("Exception in Fetch User By Id Exception {}", e.getMessage());
            throw e;
        }
    }

    /**
     * For Updating User Details
     *
     * @return Users
     */
    public Users updateUserDetails(Long userId, Users user) {
        try {
            log.info("Update User Details Service Called, User Id {}", userId);
            Optional<Users> existingOptionalUser = userRepository.findById(userId);

            if (existingOptionalUser.isPresent()) {
                Users existingUser = existingOptionalUser.get();
                if (!existingUser.getEmail().equals(user.getEmail())) {
                    Optional<Users> existingUserWithUpdatedEmail = userRepository.findByEmail(user.getEmail());
                    if (existingUserWithUpdatedEmail.isPresent()) {
                        throw new DuplicateEmailException("A user with the email " + user.getEmail() + " already exists");
                    }
                }
                existingUser.setName(user.getName());
                existingUser.setEmail(user.getEmail());
                existingUser.setDesignation(user.getDesignation());
                Users updatedUser = userRepository.save(existingUser);

                log.info("Update User Details Service Returning Updated User");
                return updatedUser;
            } else {
                log.error("Update User Details throws UserNotFoundException");
                throw new UserNotFoundException("User Not Found With ID : " + userId);
            }
        } catch (Exception e) {
            log.error("Exception in Update User Details Exception {}", e.getMessage());
            throw e;
        }
    }

    /**
     * For Deleting A User
     *
     * @return Boolean
     */
    public Boolean deleteUser(Long userId) {
        try {
            log.info("Delete User Service Called, User Id {}", userId);
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<Users> user = userRepository.findById(userId);
            if (user.isPresent()) {
                if (user.get().getEmail().equals(email)) {
                    log.error("Delete User throws DataIntegrityViolationCustomException");
                    throw new DataIntegrityViolationCustomException("You cannot delete yourself");
                }

            //ticketRepository.removeAssigneeReferences(user.get().getId());
                           userRepository.deleteById(userId);
                log.info("Delete User Service Returning True User");
                return true;
            } else {
                log.error("Delete User throws UserNotFoundException");
                throw new UserNotFoundException("User Not Found With ID: " + userId);
            }
        } catch (DataIntegrityViolationException e) {
            log.error("DataIntegrityViolationException in Delete User Exception {}", e.getMessage());
            throw new DataIntegrityViolationCustomException("Unable to delete the user. Check for associated records before deleting.");
        } catch (Exception e) {
            log.error("Exception in Delete User Exception {}", e.getMessage());
            throw e;
        }
    }


    /**
     * For changing User Password
     *
     * @return ResponseEntity
     */
    public boolean changeUserPassword(ChangePasswordDTO changePasswordDTO) {
        try {
            log.info("Change User Password Service Called");
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            log.info("Change User Password Service Called {}", email);
            if(!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword()))
            {
                log.info("Change User Password Not Matching With New Password ");
                throw new PasswordNotMatchException("Passwords should match.");
            }
            Optional<Users> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.get().getPassword())) {
                    log.info("Change User Password Not Matching With Current User");
                    throw new PasswordNotMatchException("Password Reset Failed: Invalid request or invalid password.");
                }
                if(passwordEncoder.matches(changePasswordDTO.getNewPassword(), user.get().getPassword()))
                {
                    log.info("Change User Password Service Returning SamePasswordException");
                    throw new SamePasswordException("Cannot be same as old Password");
                }

                user.get().setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
                userRepository.save(user.get());
                log.info("Change User Password ,Password changed successfully.");
                 return true;
            } else {
                log.error("Change User Password throws UserNotFoundException");
                throw new UserNotFoundException("User Not Found With Email: " + email);
            }
        } catch (DataIntegrityViolationException e) {
            log.error("DataIntegrityViolationException in Delete User Exception {}", e.getMessage());
            throw new DataIntegrityViolationCustomException("Unable to Update the user password. Check for associated records before updating.");
        } catch (Exception e) {
            log.error("Exception in Delete User Exception {}", e.getMessage());
            throw e;
        }
    }

    /**
     * For Getting Projects Of User
     *
     * @return ProjectDTO
     */
    public List<ProjectDTO> fetchProjectByUserId(Long userId) {

        log.info("Fetch Project By User Id Service Called, User Id {}", userId);
        try{
            Optional<UsersDTO> usersDTO=userRepository.findUserDtoById(userId);
            if(usersDTO.isEmpty())
            {
                log.info("Fetch Project By User Id Service Called Throw UserNotFoundException, User Id {}", userId);
                throw  new UserNotFoundException("User Not Found");
            }
            Optional<List<ProjectDTO>> projectDTOS= projectRepository.findProjectsByUserId(userId);
            if(projectDTOS.isEmpty())
            {
                log.info("Fetch Project By User Id Service Called Throw ProjectNotFoundException, User Id {}", userId);
                throw  new ProjectNotFoundException("Project Not Found");
            }
            return projectDTOS.get();
        }
        catch (Exception e){
            log.error("Exception in Fetch Project By User Exception {}", e.getMessage());
            throw  e;
        }
    }

    /**
     * For Getting Projects Of User
     *
     * @return ProjectDTO
     */
    public List<TeamDTO> fetchTeamsByUserId(Long userId) {

        log.info("Fetch Teams By User Id Service Called, User Id {}", userId);
        try{
            Optional<UsersDTO> usersDTO=userRepository.findUserDtoById(userId);
            if(usersDTO.isEmpty())
            {
                log.info("Fetch Project By User Id Service Called Throw UserNotFoundException, User Id {}", userId);
                throw  new UserNotFoundException("User Not Found");
            }
            Optional<List<TeamDTO>> teamDTOS= teamRepository.findTeamsByUserId(userId);
            if(teamDTOS.isEmpty())
            {
                log.info("Fetch Team By User Id Service Called Throw TeamNotFoundException, User Id {}", userId);
                throw  new TeamNotFoundException("Teams Not Found");
            }
            return teamDTOS.get();
        }
        catch (Exception e){
            log.error("Exception in Fetch Teams By User Id Exception {}", e.getMessage());
            throw  e;
        }
    }

    /**
     * For Fetching User By Email
     * 
     * @return UsersDTO
     */
    public UsersDTO fetchUserByEmail(String email) {
        try {
            log.info("Fetch User By ID Service Called, email {}", email);
            Optional<UsersDTO> usersDTO = userRepository.findUserDtoByEmail(email);
            if (usersDTO.isPresent()) {
                log.info("Fetch User By ID Service Returning UsersDTO");
                return usersDTO.get();
            } else {
                log.error("Fetch User By Id throws UserNotFoundException");
                throw new UserNotFoundException("User Not Found With Email : " + email);
            }
        } catch (Exception e) {
            log.error("Exception in Fetch User By Id Exception {}", e.getMessage());
            throw e;
        }
    }

    /**
     * For Getting Projects Of User
     *
     * @return ProjectDTO
     */
    public List<ProjectDTO> fetchProjectByUserEmail(String email) {

        log.info("Fetch Project By User email Service Called, User Id {}", email);
        try{
            Optional<UsersDTO> usersDTO=userRepository.findUserDtoByEmail(email);
            if(usersDTO.isEmpty())
            {
                log.info("Fetch Project By User email Service Called Throw UserNotFoundException, User Id {}", email);
                throw  new UserNotFoundException("User Not Found");
            }
            Optional<List<ProjectDTO>> projectDTOS= projectRepository.findProjectsByUserEmail(email);
            if(projectDTOS.isEmpty())
            {
                log.info("Fetch Project By User email Service Called Throw ProjectNotFoundException, User Id {}", email);
                throw  new ProjectNotFoundException("Project Not Found");
            }
            return projectDTOS.get();
        }
        catch (Exception e){
            log.error("Exception in Fetch Project By User email Exception {}", e.getMessage());
            throw  e;
        }
    }

}