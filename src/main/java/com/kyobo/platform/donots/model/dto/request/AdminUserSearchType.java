package com.kyobo.platform.donots.model.dto.request;

public enum AdminUserSearchType {

    ADMIN_ID("ADMIN_ID"),
    ADMIN_ROLE("ADMIN_ROLE"),
    REGEDIT_ADMIN_ID("REGEDIT_ADMIN_ID"),
    ALL("ALL");

    public String type;
    AdminUserSearchType(String type) {
        this.type = type;
    }
}
