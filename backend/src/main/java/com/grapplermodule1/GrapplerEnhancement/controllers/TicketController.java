//package com.grapplermodule1.GrapplerEnhancement.controllers;
//
//import com.grapplermodule1.GrapplerEnhancement.customexception.CustomResponse;
//import com.grapplermodule1.GrapplerEnhancement.customexception.ProjectNotFoundException;
//import com.grapplermodule1.GrapplerEnhancement.customexception.TicketNotFoundException;
//import com.grapplermodule1.GrapplerEnhancement.customexception.UserNotFoundException;
//import com.grapplermodule1.GrapplerEnhancement.dtos.TicketsDTO;
//import com.grapplermodule1.GrapplerEnhancement.service.TicketService;
//import jakarta.validation.Valid;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/tickets")
//public class TicketController {
//
//    private static final Logger log = LoggerFactory.getLogger(TicketController.class);
//
//    @Autowired
//    private TicketService ticketService;
//
//    /**
//     * For Getting List Of Ticket In Project
//     *
//     * @return ResponseEntity<?>
//     */
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/{projectId}")
//    public ResponseEntity<?> getTicketsByProjectId(@Valid @PathVariable("projectId") Long projectId) {
//        String debugUuid = UUID.randomUUID().toString();
//        try {
//            log.info("UUID {} Inside Get Tickets By Project Id, Project Id {} ", debugUuid, projectId);
//            List<TicketsDTO> ticketList = ticketService.fetchTicketsByProjectId(projectId);
//            log.info("Get Tickets By Project Id, Returning Tickets in ResponseEntity, Project Id {} ", projectId);
//            return new ResponseEntity<>(ticketList, HttpStatus.OK);
//        } catch (ProjectNotFoundException e) {
//            log.error("UUID {}, ProjectNotFoundException in Get Tickets BY Project Id API, Exception {}", debugUuid, e.getMessage());
//            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
//        } catch (TicketNotFoundException e) {
//            log.error("UUID {}, TicketNotFoundException in Get Tickets BY Project Id API, Exception {}", debugUuid, e.getMessage());
//            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            log.error("UUID {} Exception In Get Tickets By Project Id API Exception {}", debugUuid, e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//    /**
//     * For Fetching Tickets By User id
//     *
//     * @return List<?>
//     */
//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
//    @GetMapping("user/{userId}")
//    public ResponseEntity<?> getTicketsByUserId(@Valid @PathVariable("userId") Long userId) {
//        String debugUuid = UUID.randomUUID().toString();
//        try {
//            log.info("UUID {} Inside Get Tickets By User Id, Project Id {} ", debugUuid, userId);
//            List<TicketsDTO> ticketList = ticketService.fetchTicketsByUserId(userId);
//            log.info("Get Tickets By User Id, Returning Tickets in ResponseEntity, User Id {} ", userId);
//            return new ResponseEntity<>(ticketList, HttpStatus.OK);
//        } catch (UserNotFoundException e) {
//            log.error("UUID {}, UserNotFoundException in Get Tickets BY User Id API, Exception {}", debugUuid, e.getMessage());
//            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
//        } catch (TicketNotFoundException e) {
//            log.error("UUID {}, TicketNotFoundException in Get Tickets BY User Id API, Exception {}", debugUuid, e.getMessage());
//            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            log.error("UUID {} Exception In Get Tickets By User Id API Exception {}", debugUuid, e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * For Creating Tickets By Project I'd
//     *
//     * @return ResponseEntity<?>
//     */
//    @PostMapping("/")
//    public ResponseEntity<?> createTicketsInProject(
//            @Valid @RequestBody TicketsDTO ticket) {
//        String debugUuid = UUID.randomUUID().toString();
//        try {
//            log.info("UUID {} Inside Create Ticket In Project, Project Id {} ", debugUuid, ticket.getProjectId());
//            Ticket addedTicket = ticketService.createTicketsInProject(ticket);
//            log.info("Create Ticket By Project Id, Returning Tickets in ResponseEntity, Project Id {} ", ticket.getProjectId());
//            return new ResponseEntity<>((new CustomResponse<>(true, "Ticket Created Successfully With Id :"+addedTicket.getId(), null)) ,HttpStatus.OK);
//
//        } catch (ProjectNotFoundException e) {
//            log.error("UUID {}, ProjectNotFoundException in Create Tickets BY Project Id API, Exception {}", debugUuid, e.getMessage());
//            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
//        }
//        catch (UserNotFoundException e) {
//            log.error("UUID {}, UserNotFoundException in Create Tickets BY Project Id API, Exception {}", debugUuid, e.getMessage());
//            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
//        }
//        catch (Exception e) {
//            log.error("UUID {} Exception In Create Tickets By Project Id API Exception {}", debugUuid, e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * For Deleting Tickets
//     *
//     * @return ResponseEntity<?>
//     */
//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/{ticketId}")
//    public ResponseEntity<?> deleteTicketById(@Valid @PathVariable("ticketId") Long ticketId) {
//        String debugUuid = UUID.randomUUID().toString();
//        try {
//            log.info("UUID {} Inside Delete Ticket By Ticket Id, Ticket Id {} ", debugUuid, ticketId);
//            Boolean deleteStatus = ticketService.deleteTicketById(ticketId);
//            log.info("Delete Ticket By Ticket Id, Returning Delete Status in ResponseEntity, Ticket Id {} ", ticketId);
//            return new ResponseEntity<>((new CustomResponse<>(deleteStatus, "Ticket Deleted Successfully", null)) ,HttpStatus.OK);
//        }  catch (TicketNotFoundException e) {
//            log.error("UUID {}, TicketNotFoundException in Delete Ticket By Ticket Id API, Exception {}", debugUuid, e.getMessage());
//            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            log.error("UUID {} Exception In Delete Ticket By Ticket Id API Exception {}", debugUuid, e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * For Fetching Tickets
//     *
//     * @return List<?>
//     */
//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
//    @GetMapping("/")
//    public ResponseEntity<?> getAllTickets() {
//        String debugUuid = UUID.randomUUID().toString();
//        try {
//            log.info("UUID {} Inside Get All Tickets  ", debugUuid);
//            List<TicketsDTO> ticketList = ticketService.fetchAllTickets();
//            log.info("Get All Tickets  Returning Tickets in ResponseEntity");
//            return new ResponseEntity<>(ticketList, HttpStatus.OK);
//        }  catch (TicketNotFoundException e) {
//              log.error("UUID {}, TicketNotFoundException in Get All Tickets  Exception {}", debugUuid, e.getMessage());
//            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), null), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            log.error("UUID {} Exception In Get All Tickets API Exception {}", debugUuid, e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//}
