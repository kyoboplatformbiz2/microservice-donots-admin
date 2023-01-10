package com.kyobo.platform.donots.common.exception;

public class NotAuthorizedException  extends BusinessException {

    public NotAuthorizedException() {
        super(ErrorCode.NOT_AUTHORIZED.status, ErrorCode.NOT_AUTHORIZED.message);
    }

    public NotAuthorizedException(String caseSpecificMessage) {
        super(ErrorCode.NOT_AUTHORIZED.status, caseSpecificMessage);
    }
}