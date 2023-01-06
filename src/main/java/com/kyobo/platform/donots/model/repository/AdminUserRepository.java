package com.kyobo.platform.donots.model.repository;

import com.kyobo.platform.donots.model.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    AdminUser findByAdminId(String adminId);


}