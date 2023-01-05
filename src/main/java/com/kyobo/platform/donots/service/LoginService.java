package com.kyobo.platform.donots.service;

import com.kyobo.platform.donots.model.AdminUser;
import com.kyobo.platform.donots.repository.AdminUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class LoginService implements UserDetailsService {
    private AdminUserRepository adminUserRepository;

    public LoginService(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String adminId) throws UsernameNotFoundException {

        //adminUser 정보 조회
        Optional<AdminUser> adminUser = adminUserRepository.findByAdminId(adminId);

        if (adminUser.isPresent()) {
            AdminUser admin = adminUser.get();

            AdminUser authAdmin = AdminUser.builder()
                    .id(admin.getId())
                    .adminId(admin.getAdminId())
                    .password(admin.getPassword())
                    .role(admin.getRole())
                    .adminName(admin.getAdminName())
                    .createdAt(admin.getCreatedAt())
                    .updatedAt(admin.getUpdatedAt())
                    .build();

            log.info("authAdmin : {}", authAdmin);
            return authAdmin;
        }
        return null;
    }
}