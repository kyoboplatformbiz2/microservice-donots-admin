package com.kyobo.platform.donots.model.entity.service.account;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "SOCIAL_ACCOUNT")
public class SocialAccount {

    @Id
    @Column(name = "SOCIAL_ACCOUNT_KEY")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialAccountKey;

    @Column(name = "SOCIAL_ACCOUNT_ID", length = 64, unique = true)
    private String socialAccountId;

    @Column(name= "ACCOUNT_KEY")
    private Long accountKey;

    @Column(name = "USERNAME", length = 100)
    private String username;

    @JsonIgnore
    @Column(name = "PASSWORD", length = 128)
    private String password;

    @Column(name = "EMAIL", length = 128)
    private String email;

    @Column(name = "EMAIL_VERIFIED_YN", length = 1)
    private String emailVerifiedYn;

    @Column(name = "PROFILE_IMAGE_URL", length = 512)
    private String profileImageUrl;

    @Column(name = "GENDER", length = 8)
    private String gender;

    @Column(name = "PHONE_NUMBER", length = 64)
    private String phoneNumber;

    @Column(name = "NICKNAME", length = 64)
    private String nickname;

    @Column(name = "BIRTH_DAY", length = 8)
    private String birthDay;

    @Column(name = "BIRTH_YEAR", length = 8)
    private String birthYear;

    @Column(name = "PROVIDER_TYPE", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Column(name = "ROLE_TYPE", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    public SocialAccount(String socialAccountId, String username, String email, String emailVerifiedYn, String profileImageUrl,
                         ProviderType providerType, RoleType roleType, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.socialAccountId = socialAccountId;
        this.username = username;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.emailVerifiedYn = emailVerifiedYn;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public void updateAccountKey(Long accountKey){
        this.accountKey = accountKey;
    }

    public void updateUsername(String name){
        this.username = name;
    }

    public void updateProfileImageUrl(String profileImageUrl){
        this.profileImageUrl = profileImageUrl;
    }
}
