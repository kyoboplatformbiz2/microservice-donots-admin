package com.kyobo.platform.donots.common.exception;

public class AlreadyRegisteredIdException extends BusinessException {

    public AlreadyRegisteredIdException() {
        super(ErrorCode.ALREADY_REGISTERED_ID.status, ErrorCode.ALREADY_REGISTERED_ID.message);
    }

    public AlreadyRegisteredIdException(String caseSpecificMessage) {
        super(ErrorCode.ALREADY_REGISTERED_ID.status, caseSpecificMessage);
    }
}