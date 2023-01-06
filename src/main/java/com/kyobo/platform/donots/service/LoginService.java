package com.kyobo.platform.donots.service;

import com.kyobo.platform.donots.common.exception.AdminUserNotFoundException;
import com.kyobo.platform.donots.common.exception.AlreadyRegisteredIdException;
import com.kyobo.platform.donots.common.exception.PasswordNotMatchException;
import com.kyobo.platform.donots.model.dto.request.ChangePasswordRequest;
import com.kyobo.platform.donots.model.dto.request.CreateAdminUserRequest;
import com.kyobo.platform.donots.model.entity.AdminUser;
import com.kyobo.platform.donots.model.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService implements UserDetailsService {
    private final AdminUserRepository adminUserRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final HttpSession httpSession;

    public UserDetails createAdminUser(CreateAdminUserRequest createAdminUserRequest) {
        AdminUser adminUser = adminUserRepository.findByAdminId(createAdminUserRequest.getAdminId());

        if(adminUser != null) {
            throw new AlreadyRegisteredIdException();
        }

        LocalDateTime now = LocalDateTime.now();
        adminUser = AdminUser.builder()
                .adminId(createAdminUserRequest.getAdminId())
                .password(encoder.encode(createAdminUserRequest.getPassword()))
                .adminUserName(createAdminUserRequest.getAdminUserName())
                .adminUserNumber(createAdminUserRequest.getAdminUserNumber())
                .departmentName(createAdminUserRequest.getDepartmentName())
                .phoneNumber(createAdminUserRequest.getPhoneNumber())
                .email(createAdminUserRequest.getEmail())
                .reasonsForAuthorization(createAdminUserRequest.getReasonsForAuthorization())
                .role("ROLE_ADMIN")
                .createdDate(now)
                .lastSignInDate(now)
                .build();
        adminUserRepository.save(adminUser);

        //httpSession.setAttribute();

        return adminUser;
    }




    @Override
    public UserDetails loadUserByUsername(String adminId) throws UsernameNotFoundException {

        //adminUser 정보 조회
//        Optional<AdminUser> adminUser = adminUserRepository.findByAdminId(adminId);
//
//        if (adminUser.isPresent()) {
//            AdminUser admin = adminUser.get();
//
//            AdminUser authAdmin = AdminUser.builder()
//                    .id(admin.getId())
//                    .adminId(admin.getAdminId())
//                    .password(admin.getPassword())
//                    .role(admin.getRole())
//                    .adminUserName(admin.getAdminUserName())
//                    .createdAt(admin.getCreatedAt())
//                    .updatedAt(admin.getUpdatedAt())
//                    .build();
//
//            log.info("authAdmin : {}", authAdmin);
//            return authAdmin;
//        }
        return null;
    }


    @Transactional
    public Map changePasswordRequest(ChangePasswordRequest changePasswordRequest) {
        AdminUser adminUser = adminUserRepository.findByAdminId(changePasswordRequest.getAdminId());
        if(adminUser == null){
            throw new AdminUserNotFoundException();
        }

        if(!encoder.matches(changePasswordRequest.getPassword(), adminUser.getPassword())){
            throw new PasswordNotMatchException();
        }


        adminUser.updatePassword(changePasswordRequest.getNewPassword());

        Map<String, Boolean> result = new HashMap<>();
        result.put("updateSuccess", true);
        return result;
    }
}