package com.grapplermodule1.GrapplerEnhancement.repository;

import com.grapplermodule1.GrapplerEnhancement.entities.OtpStore;
import com.grapplermodule1.GrapplerEnhancement.entities.Permission;
import com.grapplermodule1.GrapplerEnhancement.enums.PermissionType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpStore, Integer> {

    Optional<OtpStore> findByOtp(Integer otp);
}
