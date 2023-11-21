package com.grapplermodule1.GrapplerEnhancement.controllers;
import com.grapplermodule1.GrapplerEnhancement.customexception.*;
import com.grapplermodule1.GrapplerEnhancement.dtos.ChangePasswordDTO;
import com.grapplermodule1.GrapplerEnhancement.dtos.ProjectDTO;
import com.grapplermodule1.GrapplerEnhancement.dtos.TeamDTO;
import com.grapplermodule1.GrapplerEnhancement.dtos.UsersDTO;
import com.grapplermodule1.GrapplerEnhancement.validations.PostValidation;
import com.grapplermodule1.GrapplerEnhancement.validations.PutValidation;
import com.grapplermodule1.GrapplerEnhancement.entities.Users;
import com.grapplermodule1.GrapplerEnhancement.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;


import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

//    /**
//     * For Getting List Of Users
//    *
//     * @return ResponseEntity<?>
//     */
//    @GetMapping("/")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> getAllUsers() {
//        String debugUuid = UUID.randomUUID().toString();
//        try {
//            log.info("Get All Users API Called, UUID {}", debugUuid);
//            List<UsersDTO> usersList = userService.fetchAllUsers();
//
//            return new ResponseEntity<>(usersList, HttpStatus.OK);
//        }
//        catch (UserNotFoundException e) {
//            log.error("UUID {} UserNotFoundException In Get All Users API Exception {}", debugUuid, e.getMessage());
//            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
//        }
//        catch (Exception e) {
//            log.error("UUID {} Exception In Get All Users API Exception {}", debugUuid, e.getMessage());
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @GetMapping("/")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "true") boolean isPagination
    ) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("Get All Users API Called, UUID {}", debugUuid);
            if (isPagination && page >= 0 && size >= 0) {
                // Pagination parameters are provided, fetch paginated results
                Page<UsersDTO> usersPage = userService.fetchAllUsersByPagination(PageRequest.of(page, size));
                return new ResponseEntity<>(usersPage, HttpStatus.OK);
            } else {
                List<UsersDTO> usersList = userService.fetchAllUsers();
                return new ResponseEntity<>(usersList, HttpStatus.OK);
            }
        } catch (UserNotFoundException e) {
            log.error("UUID {} UserNotFoundException In Get All Users API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("UUID {} Exception In Get All Users API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * For Create New User
     *
     * @return ResponseEntity
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> createUser(@Valid @RequestBody Users user) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("Get Create User API Called, UUID {}", debugUuid);
            UsersDTO newUser = userService.addUser(user);
            if (newUser != null) {
                return new ResponseEntity<>(new CustomResponse<>(true, "User Created With Id : " + newUser.getId(),newUser.getId()), HttpStatus.CREATED);
            }
            else {
                log.error("UUID {} User Not Created", debugUuid);
                return new ResponseEntity<>(new CustomResponseMessage(false, "User Not Created. Please Try Again"), HttpStatus.BAD_GATEWAY);
            }
        }
         catch (DuplicateEmailException e) {
            log.error("UUID {} DuplicateEmailException In Create User API Exception {}", debugUuid, e.getMessage());
             return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.CONFLICT);
        }
        catch (DataIntegrityViolationCustomException e) {
            log.error("UUID {} DataIntegrityViolationCustomException In Create User API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        catch (ValidationException e) {
            log.error("UUID {} ValidationException In Create User API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            log.error("UUID {} Exception In Create User API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false ,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * For getting User By I'd
     *
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@Valid @PathVariable("userId") Long userId) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("UUID {} Inside Get User By Id, User Id {} ", debugUuid, userId);
            UsersDTO user = userService.fetchUserById(userId);

            log.info("Get User By Id Returning User in ResponseEntity, User Id {} ", user.getId());
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (UserNotFoundException e) {
            log.error("UUID {}, UserNotFoundException in Get User BY Id API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (DuplicateEmailException e) {
            log.error("UUID {}, DuplicateEmailException in Get User BY Id API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            log.error("UUID {} Exception In Get User By Id API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * For getting User project
     *
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/project/{userId}")
    public ResponseEntity<?> getProjectsById(@Valid @PathVariable("userId") Long userId) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("UUID {} Inside Get Project By User Id, User Id {} ", debugUuid, userId);
            List<ProjectDTO> listOptional = userService.fetchProjectByUserId(userId);
            log.info("Get Project By User Id, Returning User in ResponseEntity, User Id  ");
            return new ResponseEntity<>(new CustomResponse<>(true, "Projects Of User", listOptional), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            log.error("UUID {}, UserNotFoundException in Project By User Id, API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (ProjectNotFoundException e) {
            log.error("UUID {}, ProjectNotFoundException in Project By User Id, API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("UUID {} Exception In Project By User Id, API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * For Update User By Id
     *
     * @return ResponseEntity
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserById(@PathVariable("userId") Long userId,
                                            @Validated(PutValidation.class) @RequestBody Users user) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("UUID {} Inside Update User By Id, User Id {} ", debugUuid, userId);
            Users updatedUser = userService.updateUserDetails(userId, user);

            log.info("Update User By Id Returning User in ResponseEntity, Updated User Id {} ", updatedUser.getId());
            return new ResponseEntity<>(new CustomResponseMessage(true, "User Updated Successfully."), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            log.error("UUID {}, UserNotFoundException in Update User BY Id API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (DuplicateEmailException e) {
            log.error("UUID {} DuplicateEmailException In Update User API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            log.error("UUID {} Exception In Update User By Id API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * For Delete User By ID
     *
     * @return ResponseEntity
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") Long userId) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("UUID {} Inside Delete User By Id, User Id {} ", debugUuid, userId);
            Boolean deleteUser = userService.deleteUser(userId);

            log.info("UUID {} Delete User By Id Returning Boolean Value True in ResponseEntity ", debugUuid);
            return new ResponseEntity<>(new CustomResponseMessage(deleteUser, "User Deleted Successfully."), HttpStatus.OK);
        }
        catch (UserNotFoundException e) {
            log.error("UUID {}, UserNotFoundException in Delete User BY Id API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (DataIntegrityViolationCustomException e) {
            log.error("UUID {}, DataIntegrityViolationCustomException   in Delete User BY Id API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        catch (Exception e) {
            log.error("UUID {} Exception In Get User By Id API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * For changing User Password
     *
     * @return ResponseEntity
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/change-password")
    public ResponseEntity<?> ChangePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO)
    {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("UUID {} Inside ChangePassword,", debugUuid);
            boolean status = userService.changeUserPassword(changePasswordDTO);

            log.info("Change User Password Returning True in ResponseEntity ");
            return new ResponseEntity<>(new CustomResponseMessage(true, "Password Updated Successfully."), HttpStatus.OK);
        }
        catch (UserNotFoundException e) {
            log.error("UUID {}, UserNotFoundException in change User Password BY Id API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (PasswordNotMatchException e) {
            log.error("UUID {} PasswordNotMatchException In Change User Password API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        catch (SamePasswordException e)
        {
            log.error("UUID {} SamePasswordException In Change User Password API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            log.error("UUID {} Exception In Change User Password By Id API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
   }

    /**
     * For Getting Teams of User
     *
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("teams/{userId}")
    public ResponseEntity<?> getTeamsById(@Valid @PathVariable("userId") Long userId) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("UUID {} Inside Get Project By User Id, User Id {} ", debugUuid, userId);
            List<TeamDTO> listOptional= userService.fetchTeamsByUserId(userId);
            log.info("Get Project By User Id, Returning User in ResponseEntity, User Id  " );
            return new ResponseEntity<>(new CustomResponse<>(true,"Teams Of User",listOptional), HttpStatus.OK);
        }
        catch (UserNotFoundException e) {
            log.error("UUID {}, UserNotFoundException in Project By User Id, API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (TeamNotFoundException e) {
            log.error("UUID {}, TeamNotFoundException in Project By User Id, API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            log.error("UUID {} Exception In Project By User Id, API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * For Getting User By Email
     *
     * @return ResponseEntity<?>
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("UUID {} Inside Get User By Id, Email {} ", debugUuid, email);
            UsersDTO user = userService.fetchUserByEmail(email);

            log.info("Get User By Id Returning User in ResponseEntity, User Id {} ", user.getId());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            log.error("UUID {}, UserNotFoundException in Get User BY Id API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (DuplicateEmailException e) {
            log.error("UUID {}, DuplicateEmailException in Get User BY Id API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.error("UUID {} Exception In Get User By Id API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * For getting User project
     *
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/project/email/{email}")
    public ResponseEntity<?> getProjectsByEmail(@Valid @PathVariable("email") String email) {
        String debugUuid = UUID.randomUUID().toString();
        try {
            log.info("UUID {} Inside Get Project By User email, User Id {} ", debugUuid, email);
            List<ProjectDTO> listOptional = userService.fetchProjectByUserEmail(email);
            log.info("Get Project By User Id, Returning User in ResponseEntity, User Id  ");
            return new ResponseEntity<>(new CustomResponse<>(true, "Projects Of User", listOptional), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            log.error("UUID {}, UserNotFoundException in Project By User email, API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (ProjectNotFoundException e) {
            log.error("UUID {}, ProjectNotFoundException in Project By User email, API, Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(new CustomResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("UUID {} Exception In Project By User email, API Exception {}", debugUuid, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}