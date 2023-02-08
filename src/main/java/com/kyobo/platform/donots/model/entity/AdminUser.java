package com.kyobo.platform.donots.model.entity;

import com.kyobo.platform.donots.model.dto.request.ModifyAdminUserRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ADMIN_USER")
public class AdminUser implements UserDetails {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ADMIN_ID")
    private String adminId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ADMIN_USER_NAME")
    private String adminUserName;

    @Column(name = "ADMIN_USER_NUMBER")
    private String adminUserNumber;

    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;
    @Column(name = "ADMIN_ROLE")
    private String role;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MEMO")
    private String memo;

    @Column(name = "ATTACH_IMAGE_URL")
    private String attachImageUrl;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createdDate;

    @Column(name = "LAST_SIGN_IN_DATE")
    private LocalDateTime lastSignInDate;

    @Column(name = "LAST_PASSWORD_CHANGE_DATE")
    private LocalDateTime lastPasswordChangeDate;

    @Column(name = "REASONS_FOR_AUTHORIZATION")
    private String reasonsForAuthorization;

    @Column(name = "REGEDIT_ADMIN_ID")
    private String regeditAdminId;

    @Column(name = "SESSION_ID")
    private String sessionId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();

        for (String role : role.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    @Override
    public String getUsername() {
        return adminId;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void updatePassword(String newPassword) {
        LocalDateTime now = LocalDateTime.now();
        this.password = newPassword;
        this.lastPasswordChangeDate = now;
    }

    public void updateModifyAdminUser(ModifyAdminUserRequest modifyAdminUserRequest) {
        this.adminUserName = modifyAdminUserRequest.getAdminUserName();
        this.departmentName = modifyAdminUserRequest.getDepartmentName();
        this.adminUserNumber = modifyAdminUserRequest.getAdminUserNumber();
        this.phoneNumber = modifyAdminUserRequest.getPhoneNumber();
        this.regeditAdminId = modifyAdminUserRequest.getRegeditAdminId();
        this.role = modifyAdminUserRequest.getRole();
        this.reasonsForAuthorization = modifyAdminUserRequest.getReasonsForAuthorization();
        this.email = modifyAdminUserRequest.getEmail();
        this.memo = modifyAdminUserRequest.getMemo();
        this.attachImageUrl = modifyAdminUserRequest.getAttachImageUrl();
    }

    public void updateAttachImageUrl(String attachImageUrl){
        this.attachImageUrl = attachImageUrl;
    }

    public void updateSessionId(String sessionId){
        this.sessionId = sessionId;
    }

    public void updateLastPasswordChangeDate() {
        this.lastPasswordChangeDate = LocalDateTime.now();
    }
}

