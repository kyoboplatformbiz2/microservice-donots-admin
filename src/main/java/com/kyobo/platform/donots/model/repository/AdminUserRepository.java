package com.kyobo.platform.donots.model.repository;

import com.kyobo.platform.donots.model.entity.AdminUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    AdminUser findByAdminId(String adminId);
    void deleteById(Long id);
    boolean existsByAdminId(String adminId);

    Page<AdminUser> findByAdminIdContaining(String search, Pageable pageable);

    Page<AdminUser> findByRole(String search, Pageable pageable);

    Page<AdminUser> findByAdminUserNameContaining(String search, Pageable pageable);
}