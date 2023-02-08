package com.kyobo.platform.donots.model.entity.service.account;

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
@Table(name = "ACCOUNT")
public class Account {

    @Id
    @Column(name = "ACCOUNT_KEY")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountKey;

    @Column(name = "ID", unique = true)
    private String id;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "CI", length = 100)
    private String ci;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "BIRTH_DAY", length = 12)
    private String birthDay;

    @Column(name = "GENDER", length = 8)
    private String gender;

    @Column(name = "PHONE_NUMBER", length = 64)
    private String phoneNumber;

    @Column(name = "ROLE_TYPE", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "LAST_SIGN_IN_AT")
    private LocalDateTime lastSignInAt;

    public void updatePassword(String password){
        this.password = password;
    }
    public void updatePhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void updateIdSignUp(String id, String name, String password, String gender, String phoneNumber, String birthDay, RoleType roleType) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.roleType = roleType;
    }

    public void regeditSnsSignUpAddId(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
