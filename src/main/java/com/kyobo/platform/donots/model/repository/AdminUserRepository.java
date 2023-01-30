package com.kyobo.platform.donots.model.repository;

import com.kyobo.platform.donots.model.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    AdminUser findByAdminId(String adminId);
    void deleteById(Long id);
    boolean existsByAdminId(String adminId);

}