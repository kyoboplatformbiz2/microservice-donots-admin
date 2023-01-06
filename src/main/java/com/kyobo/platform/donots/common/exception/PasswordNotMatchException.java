package com.kyobo.platform.donots.common.exception;

public class PasswordNotMatchException extends BusinessException {

    public PasswordNotMatchException() {
        super(ErrorCode.PASSWORD_NOT_MATCH.status, ErrorCode.PASSWORD_NOT_MATCH.message);
    }

    public PasswordNotMatchException(String caseSpecificMessage) {
        super(ErrorCode.PASSWORD_NOT_MATCH.status, caseSpecificMessage);
    }
}