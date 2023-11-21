package com.grapplermodule1.GrapplerEnhancement.repository;

import com.grapplermodule1.GrapplerEnhancement.dtos.HierarchyDTO;
import com.grapplermodule1.GrapplerEnhancement.dtos.ProjectDTO;
import com.grapplermodule1.GrapplerEnhancement.dtos.UsersDTO;
import com.grapplermodule1.GrapplerEnhancement.entities.Project;
import com.grapplermodule1.GrapplerEnhancement.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByDesignation(String designation);

    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.UsersDTO(e.id, e.name, e.email, e.designation) " +
            "FROM Users e")
    Optional<List<UsersDTO>> findAllUser();

    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.UsersDTO(e.id, e.name, e.email, e.designation) " +
            "FROM Users e")
  Page<UsersDTO> findAllUserByPagination(Pageable pageable);



    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.UsersDTO(e.id, e.name, e.email, e.designation) " +
            "FROM Users e WHERE e.id = :userId")
    Optional<UsersDTO> findUserDtoById(Long userId);

//    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.UsersDTO(e.id, e.name, e.email, e.designation) " +
//            "FROM Users e WHERE e.reportingUser.id = :reportingId")
//    Optional<List<UsersDTO>> findAllByReportingId(Long reportingId);

    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.UsersDTO(e.id, e.name, e.email, e.designation) " +
            "FROM Users e WHERE e.reportingUser.id = :reportingId")
    Optional<List<UsersDTO>> findAllByReportingId(Long reportingId);

    @Query("SELECT NEW com.grapplermodule1.GrapplerEnhancement.dtos.UsersDTO(e.id, e.name, e.email, e.designation) " +
            "FROM Users e WHERE e.email = :email")
    Optional<UsersDTO> findUserDtoByEmail(String email);



    //List<ProjectDTO> findProjectByUserId(Long userId);


}