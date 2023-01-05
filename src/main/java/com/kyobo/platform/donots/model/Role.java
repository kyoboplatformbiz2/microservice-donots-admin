package com.kyobo.platform.donots.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    SUPER_ADMIN("ROLE_SUPER_ADMIN, ROLE_ADMIN"),
    ADMIN("ROLE_ADMIN");

    private String value;
}