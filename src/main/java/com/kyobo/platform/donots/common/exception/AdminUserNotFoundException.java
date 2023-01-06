package com.kyobo.platform.donots.common.exception;

public class AdminUserNotFoundException extends BusinessException {

    public AdminUserNotFoundException() {
        super(ErrorCode.ADMIN_USER_NOT_FOUND.status, ErrorCode.ADMIN_USER_NOT_FOUND.message);
    }

    public AdminUserNotFoundException(String caseSpecificMessage) {
        super(ErrorCode.ADMIN_USER_NOT_FOUND.status, caseSpecificMessage);
    }
}