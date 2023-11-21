//package com.grapplermodule1.GrapplerEnhancement.service;
//
//import com.grapplermodule1.GrapplerEnhancement.customexception.ProjectNotFoundException;
//import com.grapplermodule1.GrapplerEnhancement.customexception.TicketNotFoundException;
//import com.grapplermodule1.GrapplerEnhancement.customexception.UserNotFoundException;
//import com.grapplermodule1.GrapplerEnhancement.dtos.TicketsDTO;
//import com.grapplermodule1.GrapplerEnhancement.entities.Project;
//import com.grapplermodule1.GrapplerEnhancement.entities.TeamMembers;
//import com.grapplermodule1.GrapplerEnhancement.entities.Users;
//import com.grapplermodule1.GrapplerEnhancement.repository.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class TicketService {
//
//    @Autowired
//    private TicketRepository ticketRepository;
//
//    @Autowired
//    private ProjectRepository projectRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private TeamRepository teamRepository;
//
//    @Autowired
//    TeamMemberRepository teamMemberRepository;
//    private static final Logger log = LoggerFactory.getLogger(TicketService.class);
//
//
//    /**
//     * For Fetching Tickets By Project id
//     *
//     * @return List<Ticket>
//     */
//    public List<TicketsDTO> fetchTicketsByProjectId(Long projectId) {
//        try {
//            log.info("Fetch Tickets By Project Id Service Called, Project Id {}", projectId);
//            Optional<Project> project = projectRepository.findById(projectId);
//            if (project.isEmpty()) {
//                log.info("Fetch Tickets By Project Throws ProjectNotFoundException, Project Id {}", projectId);
//                throw new ProjectNotFoundException("Project Not Found With Id :" + projectId);
//            }
//            Optional<List<TicketsDTO>> listOfTickets = ticketRepository.findByProjectId(projectId);
//            if (listOfTickets.isPresent() && !listOfTickets.get().isEmpty()) {
//                log.info("Fetch Tickets By Project Id Service Returning List<TicketsDTO>");
//                return listOfTickets.get();
//            } else {
//                log.error("Fetch Tickets By Project Id throws TicketNotFoundException");
//                throw new TicketNotFoundException("Tickets Not Found With Project Id : " + projectId);
//            }
//        } catch (Exception e) {
//            log.error("Exception in Fetch By Project Id Exception {}", e.getMessage());
//            throw e;
//        }
//    }
//
//
//    /**
//     * For Fetching Tickets By User id
//     *
//     * @return List<TicketsDTO>
//     */
//    public List<TicketsDTO> fetchTicketsByUserId(Long userId) {
//        try {
//            log.info("Fetch Tickets By userId Id Service Called, userId Id {}", userId);
//            Optional<Users> usersDTO = userRepository.findById(userId);
//            if (usersDTO.isEmpty()) {
//                log.error("Fetch Tickets By User Id throws UserNotFoundException");
//                throw new UserNotFoundException("User Not Found With Id :" + userId);
//            }
//            Optional<List<TicketsDTO>> listOfTickets = ticketRepository.findByUserId(userId);
//            if (listOfTickets.isPresent() && !listOfTickets.get().isEmpty()) {
//                List<TicketsDTO> tickets = listOfTickets.get();
//                for (TicketsDTO ticket : tickets) {
//                    Long ticketId = ticket.getId();
//                    List<Long> teamMemberIds = ticketRepository.findTeamMemberIds(ticketId);
//                    ticket.setAssigneeIds(teamMemberIds);
//                }
//                log.info("Fetch Tickets By User Id Service Returning List<TicketsDTO>");
//                return listOfTickets.get();
//            } else {
//                log.error("Fetch Tickets By User Id throws TicketNotFoundException");
//                throw new TicketNotFoundException("Tickets Not Found With User Id : " + userId);
//            }
//        } catch (Exception e) {
//            log.error("Exception in Fetch Ticket By User Id Exception {}", e.getMessage());
//            throw e;
//        }
//    }
//
//    /**
//     * For Creating Tickets By Project I'd
//     *
//     *  @return Ticket
//     */
//    public Ticket createTicketsInProject(TicketsDTO ticketDTO) {
//        try {
//            log.info("Create Ticket By Project Id Service Called, Project Id {}", ticketDTO.getProjectId());
//            log.info("Create Ticket By Project Id Service Called, email Id {}", ticketDTO.getCreatorEmail());
//
//            Optional<Project> project = projectRepository.findById(ticketDTO.getProjectId());
//            if (project.isEmpty()) {
//                throw new ProjectNotFoundException("Project Not Found With Id: " + ticketDTO.getProjectId());
//            }
//            Optional<Users> creator = userRepository.findByEmail(ticketDTO.getCreatorEmail());
//            if (creator.isEmpty()) {
//                throw new UserNotFoundException("User Not Found With Id: " + ticketDTO.getCreatorEmail());
//            }
//
//            Ticket newTicket = new Ticket();
//            newTicket.setName(ticketDTO.getName());
//            newTicket.setProject(project.get());
//            newTicket.setUser(creator.get());
//            newTicket.setStatus(ticketDTO.getStatus());
//            newTicket.setEndDate(ticketDTO.getEndDate());
//            newTicket.setCreatedDate(LocalDate.now());
//            newTicket.setPriority(ticketDTO.getPriority());
//
//            if (ticketDTO.getAssigneeIds() != null) {
//                List<TeamMembers> assignees = ticketDTO.getAssigneeIds().stream()
//                        .map(memberId -> {
//                            Optional<TeamMembers> teamMember = teamMemberRepository.findById(memberId);
//                            if (teamMember.isEmpty()) {
//                                throw new UserNotFoundException("Team Member Not Found With ID: " + memberId);
//                            }
//                            return teamMember.get();
//                        })
//                        .collect(Collectors.toList());
//
//                newTicket.setAssignees(assignees);
//            }
//            log.info("New Ticket {}" ,newTicket);
//            return ticketRepository.save(newTicket);
//        } catch (Exception e) {
//            log.error("Exception in Create Ticket Service: {}", e.getMessage());
//            throw e;
//        }
//    }
//
//    /**
//     * For Deleting Tickets
//     *
//     * @return Boolean
//     */
//    public Boolean deleteTicketById(Long ticketId) {
//        try {
//            log.info("Delete Ticket Service Called, Ticket Id {}", ticketId);
//            Optional<Ticket> ticket = ticketRepository.findById(ticketId);
//
//            if (ticket.isPresent()) {
//                ticketRepository.deleteById(ticketId);
//                log.info("Delete Ticket Service Returning True");
//                return true;
//            } else {
//                log.error("Delete Ticket throws TicketNotFoundException In Service");
//                throw new TicketNotFoundException("Ticket Not Found With ID : " + ticketId);
//            }
//        } catch (Exception e) {
//            log.error("Exception in Delete Ticket Exception In Service {}", e.getMessage());
//            throw e;
//        }
//    }
//
//    /**
//     * For Fetching Tickets
//     *
//     * @return List<TicketsDTO>
//     */
//    public List<TicketsDTO> fetchAllTickets() {
//        try {
//            log.info("Fetch All Tickets  Service Called");
//            Optional<List<TicketsDTO>> listOfTickets = ticketRepository.findAllTickets();
//            if (listOfTickets.isPresent() && !listOfTickets.get().isEmpty()) {
//                List<TicketsDTO> tickets = listOfTickets.get();
//                for (TicketsDTO ticket : tickets) {
//                    Long ticketId = ticket.getId();
//                    List<Long> teamMemberIds = ticketRepository.findTeamMemberIds(ticketId);
//                    ticket.setAssigneeIds(teamMemberIds);
//                }
//                log.info("Fetch All Tickets Service Returning List<TicketsDTO>");
//                return listOfTickets.get();
//            } else {
//                log.error("Fetch All Tickets  throws TicketNotFoundException");
//                throw new TicketNotFoundException("Tickets Not Found");
//            }
//        } catch (Exception e) {
//            log.error("Exception in Fetch All Tickets Exception {}", e.getMessage());
//            throw e;
//        }
//    }
//
//}
