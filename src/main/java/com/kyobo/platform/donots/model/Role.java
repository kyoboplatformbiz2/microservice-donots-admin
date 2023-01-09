package com.kyobo.platform.donots.model;

public enum Role {

    SUPER_ADMIN("ROLE_SUPER_ADMIN, ROLE_ADMIN"),
    ADMIN("ROLE_ADMIN");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}