//package com.grapplermodule1.GrapplerEnhancement.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface TicketRepository extends JpaRepository<Ticket, Long> {
////    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.TicketsDTO(t.id, t.name,t.project.id,t.project.name,t.user.id) FROM Ticket t WHERE t.project.id = :projectId")
////    Optional<List<TicketsDTO>> findByProjectId(Long projectId);
////
////    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.TicketsDTO(t.id, t.name,t.project.id,t.project.name," +
////            "t.user.name,t.endDate,t.status,t.priority,t.createdDate) " +
////            "FROM Ticket t " +
////            "JOIN t.assignees a " +  // Join with assignees
////            "WHERE a.user.id = :userId")  // Filter by userId
////     Optional<List<TicketsDTO>> findByUserId(Long userId);
////
////
////    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.TicketsDTO(t.id, t.name, t.project.id, " +
////            "t.project.name, t.user.name, t.endDate, t.status, t.priority, t.createdDate) FROM Ticket t")
////    Optional<List<TicketsDTO>> findAllTickets();
////
////    @Query("SELECT tm.id FROM Ticket t JOIN t.assignees tm WHERE t.id = :ticketId")
////    List<Long> findTeamMemberIds(@Param("ticketId") Long ticketId);
////
////    @Modifying
////    @Transactional
////    @Query("DELETE FROM TeamMembers ta WHERE ta.user.id = :userId")
////    void removeAssigneeReferences(@Param("userId") Long userId);
//
//
//
//}
//
//
//
