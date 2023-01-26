package com.kyobo.platform.donots.service;

import com.kyobo.platform.donots.common.exception.AdminUserNotFoundException;
import com.kyobo.platform.donots.common.exception.AlreadyRegisteredIdException;
import com.kyobo.platform.donots.common.exception.NotAuthorizedException;
import com.kyobo.platform.donots.common.exception.PasswordNotMatchException;
import com.kyobo.platform.donots.model.Role;
import com.kyobo.platform.donots.model.dto.request.*;
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
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService implements UserDetailsService {
    private final AdminUserRepository adminUserRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//    private final HttpSession httpSession;

    public UserDetails createAdminUser(CreateAdminUserRequest createAdminUserRequest) {
        AdminUser adminUser = adminUserRepository.findByAdminId(createAdminUserRequest.getAdminId());

        if(adminUser != null)
            throw new AlreadyRegisteredIdException();


        LocalDateTime now = LocalDateTime.now();
        adminUser = AdminUser.builder()
                .adminId(createAdminUserRequest.getAdminId())
                .password(encoder.encode(createAdminUserRequest.getPassword()))
                .adminUserName(createAdminUserRequest.getAdminUserName())
                .adminUserNumber(createAdminUserRequest.getAdminUserNumber())
                .departmentName(createAdminUserRequest.getDepartmentName())
                .phoneNumber(createAdminUserRequest.getPhoneNumber())
                .regeditAdminId(createAdminUserRequest.getRegeditAdminId())
                .email(createAdminUserRequest.getEmail())
                .reasonsForAuthorization(createAdminUserRequest.getReasonsForAuthorization())
                .role(createAdminUserRequest.getRole())
                .createdDate(now)
                .lastSignInDate(now)
                .build();
        adminUserRepository.save(adminUser);

        //httpSession.setAttribute();

        return adminUser;
    }

    @Transactional
    public void changePasswordRequest(ChangePasswordRequest changePasswordRequest) {
        AdminUser adminUser = adminUserRepository.findByAdminId(changePasswordRequest.getAdminId());
        if(adminUser == null)
            throw new AdminUserNotFoundException();
        if(!encoder.matches(changePasswordRequest.getPassword(), adminUser.getPassword()))
            throw new PasswordNotMatchException();
        adminUser.updatePassword(encoder.encode(changePasswordRequest.getNewPassword()));
    }

    public Map<String, Boolean> verification(String adminId) {
        Map<String, Boolean> result = new HashMap<>();
        boolean verification = adminUserRepository.existsByAdminId(adminId);
        result.put("verification", verification);
        return result;
    }

    @Transactional
    public void deleteAdminUser(DeleteAdminUserRequest deleteAdminUserRequest) {
        AdminUser adminUser = adminUserRepository.findByAdminId(deleteAdminUserRequest.getAdminId());
        adminUserRepository.delete(adminUser);
    }

    @Transactional
    public UserDetails modifyAdminUser(ModifyAdminUserRequest modifyAdminUserRequest) {
        AdminUser adminUser = adminUserRepository.findByAdminId(modifyAdminUserRequest.getAdminId());

        if(adminUser == null)
            throw new AdminUserNotFoundException();

        adminUser.updateModifyAdminUser(modifyAdminUserRequest);

        return adminUser;
    }


    public UserDetails signIn(SignInRequest signInRequest) {
        AdminUser adminUser = adminUserRepository.findByAdminId(signInRequest.getAdminId());
        if(adminUser == null)
            throw new AdminUserNotFoundException();

        if(!encoder.matches(signInRequest.getPassword(), adminUser.getPassword()))
            throw new PasswordNotMatchException();

        return adminUser;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser adminUser = adminUserRepository.findByAdminId(username);
        return adminUser;
    }

    public List<AdminUser> getAdminUserAll() {
        List<AdminUser> list = adminUserRepository.findAll();
        return list;
    }
}